package jp.takeda.tsuramichain.domain.service

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CalculateHashService {

    fun calculateHash(index: Long, previousHash: String, timestamp: LocalDateTime, data: String): String {
        val sb = buildString {
            append(index.toString())
            append(previousHash)
            append(timestamp.toString())
            append(data)
        }

        return DigestUtils.sha256Hex(sb.toString())
    }
}