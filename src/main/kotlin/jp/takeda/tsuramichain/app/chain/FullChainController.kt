package jp.takeda.tsuramichain.app.chain

import jp.takeda.tsuramichain.domain.model.Block
import jp.takeda.tsuramichain.domain.service.BlockchainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("chain")
class FullChainController {

    @Autowired
    lateinit var service: BlockchainService

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun mine(model: Model): ResponseEntity<List<Block>> {
        val blockChain = this.service.getFullChain()

        return ResponseEntity(blockChain, HttpStatus.OK);
    }
}