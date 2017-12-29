package jp.takeda.tsuramichain

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TsuramichainApplication

fun main(args: Array<String>) {
    SpringApplication.run(TsuramichainApplication::class.java, *args)
}
