package jp.takeda.tsuramichain.domain.model

import java.time.LocalDateTime

class Block(val index: Long, val previousHash: String, val timestamp: LocalDateTime, val data: String, val hash: String) {
}