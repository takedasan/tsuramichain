package jp.takeda.tsuramichain.domain.model

data class Block(
        var index: Long = 0L,
        var previousHash: String = "",
        var timestamp: Long = 0L,
        var transactions: MutableList<Transaction> = mutableListOf(),
        var proof: Long = 0L
)