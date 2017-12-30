package jp.takeda.tsuramichain.domain.service

import jp.takeda.tsuramichain.domain.model.Block
import jp.takeda.tsuramichain.domain.model.Transaction
import jp.takeda.tsuramichain.domain.repository.Blockchain
import jp.takeda.tsuramichain.domain.repository.TransactionCache
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class BlockchainService {

    private var blockchain: Blockchain = Blockchain()

    private var transactionCache: TransactionCache = TransactionCache()

    init {
        this.createBlock("1",100)
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
        var proof = 0L

        while (!this.validProof(lastProof, proof)) {
            proof += 1
        }

        println("found!:" + proof)
        return proof
    }

    fun getLastProof(): Long {
        return this.blockchain.chain.last().proof
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