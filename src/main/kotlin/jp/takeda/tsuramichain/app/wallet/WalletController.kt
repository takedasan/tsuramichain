package jp.takeda.tsuramichain.app.wallet

import jp.takeda.tsuramichain.domain.service.BlockchainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("wallet")
class WalletController {

    @Autowired
    lateinit var service: BlockchainService

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun getBalance(@RequestBody body: WalletForm): ResponseEntity<Long> {
        val balance = this.service.getBalanceByUuid(body.uuid);

        return ResponseEntity(balance, HttpStatus.OK);
    }

}