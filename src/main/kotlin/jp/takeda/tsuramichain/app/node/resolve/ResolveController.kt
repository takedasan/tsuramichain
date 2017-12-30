package jp.takeda.tsuramichain.app.node.resolve

import jp.takeda.tsuramichain.domain.service.BlockchainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("node/resolve")
class ResolveController {

    @Autowired
    lateinit var service: BlockchainService

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun resolve(model: Model): ResponseEntity<String> {
        val isReplaced = this.service.resoleveConflicts()

        if (isReplaced) {
            return ResponseEntity("replaced", HttpStatus.OK);
        } else {
            return ResponseEntity("not replaced", HttpStatus.OK);
        }
    }
}