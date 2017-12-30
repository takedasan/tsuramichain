package jp.takeda.tsuramichain.domain.repository

import jp.takeda.tsuramichain.domain.model.Transaction
import org.springframework.stereotype.Component

@Component
class TransactionCache {

    val cache: MutableList<Transaction> = mutableListOf()
}