package jp.takeda.tsuramichain.domain.model

class Block(val index: Long, val previousHash: String, val timestamp: Long, val transactions:  MutableList<Transaction>, val proof: Long) {
}