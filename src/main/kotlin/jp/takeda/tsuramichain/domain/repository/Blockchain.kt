package jp.takeda.tsuramichain.domain.repository

import jp.takeda.tsuramichain.domain.model.Block
import org.springframework.stereotype.Component

@Component
class Blockchain {

    val chain: MutableList<Block> = mutableListOf()
}