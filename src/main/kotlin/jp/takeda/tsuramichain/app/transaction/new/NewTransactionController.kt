package jp.takeda.tsuramichain.app.transaction.new

import jp.takeda.tsuramichain.domain.service.BlockchainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("transaction/new")
class NewTransactionController {

    @Autowired
    lateinit var service: BlockchainService

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun createTransaction(@RequestParam("sender") sender: String, @RequestParam("recipient") recipient: String, @RequestParam("amount") amount: Long): ResponseEntity<String> {
        this.service.createTransaction(sender, recipient, amount)

        return ResponseEntity(HttpStatus.OK);
    }

}