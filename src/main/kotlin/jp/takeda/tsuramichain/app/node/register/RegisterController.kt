package jp.takeda.tsuramichain.app.node.register

import jp.takeda.tsuramichain.domain.service.BlockchainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("node/register")
class RegisterController {

    @Autowired
    lateinit var service: BlockchainService

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun register(@RequestBody body: RegisterForm): ResponseEntity<String> {
        if (body.nodes.isEmpty()) {
            return ResponseEntity("node list is empty", HttpStatus.BAD_REQUEST);
        }

        for (node in body.nodes) {
            this.service.registerNode(node)
        }

        return ResponseEntity(HttpStatus.OK);
    }
}