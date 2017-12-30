package jp.takeda.tsuramichain.domain.model

data class Transaction(
        val sender: String,
        val recipient: String,
        val amount: Long
)