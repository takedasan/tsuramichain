package jp.takeda.tsuramichain.domain.model

data class Transaction(
        var sender: String = "",
        var recipient: String = "",
        var amount: Long = 0L
)