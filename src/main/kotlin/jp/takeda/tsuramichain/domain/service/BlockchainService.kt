package jp.takeda.tsuramichain.domain.service

import jp.takeda.tsuramichain.domain.model.Block
import jp.takeda.tsuramichain.domain.model.Transaction
import jp.takeda.tsuramichain.domain.repository.Blockchain
import jp.takeda.tsuramichain.domain.repository.TransactionCache
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.ZoneId


@Service
class BlockchainService {

    private var blockchain: Blockchain = Blockchain()

    private var transactionCache: TransactionCache = TransactionCache()

    private var nodeSet: MutableSet<String> = mutableSetOf()

    init {
        this.createBlock("1", 100)
    }

    fun createBlock(previousHash: String, proof: Long): Block {
        // next index
        val index = blockchain.chain.size + 1L
        // create unix time
        val now = LocalDateTime.now();
        val zoneId = ZoneId.systemDefault();
        val timestamp = now.atZone(zoneId).toEpochSecond();
        // get transactions
        val transactions = this.transactionCache.cache
        // create previous hash by prevous block
        val previousHash = if (previousHash.isNullOrBlank()) this.calculateHash(this.blockchain.chain.last()) else previousHash


        val newBlock = Block(index, previousHash, timestamp, transactions, proof)

        // Added last
        this.blockchain.chain += newBlock

        // clear cache
        this.transactionCache.cache.clear()

        return newBlock
    }

    fun createBlodk(proof: Long): Block {
        return this.createBlock("", proof)
    }

    fun createTransaction(sender: String, recipient: String, amount: Long): Transaction {
        val newTransaction = Transaction(sender, recipient, amount)

        // Added chache
        this.transactionCache.cache += newTransaction

        return newTransaction
    }

    fun proofOfWork(lastProof: Long): Long {
        var proof = lastProof

        while (!this.validProof(lastProof, proof)) {
            proof += 1
        }

        return proof
    }

    fun getFullChain(): List<Block> {
        return this.blockchain.chain
    }

    fun getLastProof(): Long {
        return this.blockchain.chain.last().proof
    }

    fun registerNode(address: String) {
        this.nodeSet.add(address)
        println(address)
    }

    fun resoleveConflicts(): Boolean {
        val otherNodeList = this.nodeSet.toList()
        var maxLength = this.blockchain.chain.size
        var newChain = Blockchain()

        val restTemplate = RestTemplate()
        for (node in otherNodeList) {
            val blockList = restTemplate.exchange("http://${node}/chain", HttpMethod.GET, null, object : ParameterizedTypeReference<List<Block>>() {
            }).body

            val length = blockList.size
            if (length > maxLength && this.validChain(blockList)) {
                maxLength = length

                newChain.chain.clear()
                newChain.chain.addAll(blockList)
            }
        }

        // replace chain
        if (!newChain.chain.isEmpty()) {
            this.blockchain.chain.clear()
            this.blockchain.chain.addAll(newChain.chain)

            return true
        }

        return false
    }

    private fun validChain(blockList: List<Block>): Boolean {
        var lastBlock = blockList.get(0)
        var currentIndex = 1

        while (currentIndex > blockList.size) {
            val block = blockList.get(currentIndex)

            if (block.previousHash != this.calculateHash(lastBlock)) {
                return false
            }

            if (!this.validProof(lastBlock.proof, block.proof)) {
                return false
            }

            // update parameter
            lastBlock = block
            currentIndex += 1
        }

        return true
    }

    private fun validProof(lastProof: Long, proof: Long): Boolean {
        val guess = (lastProof * proof).toString()
        val guessHash = DigestUtils.sha256Hex(guess)
        val length = guessHash.toString().length

        return guessHash.toString().substring(length - 5) == "00000"
    }

    private fun calculateHash(block: Block): String {
        return DigestUtils.sha256Hex(block.toString())
    }
}