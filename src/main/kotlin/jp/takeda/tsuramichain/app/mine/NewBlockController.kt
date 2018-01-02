package jp.takeda.tsuramichain.app.mine

import jp.takeda.tsuramichain.domain.service.BlockchainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("mine")
class NewBlockController {

    @Autowired
    lateinit var service: BlockchainService

    val uuid = UUID.randomUUID().toString()

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun mine(model: Model): ResponseEntity<String> {
        val lastProof = this.service.getLastProof()
        val proof = this.service.proofOfWork(lastProof)

        // reward to miner
        this.service.createTransaction("0", uuid, 1)
        this.service.createBlodk(proof)

        return ResponseEntity(uuid, HttpStatus.OK);
    }

}