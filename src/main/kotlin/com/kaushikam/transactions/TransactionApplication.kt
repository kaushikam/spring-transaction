package com.kaushikam.transactions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
@EntityScan("com.kaushikam.transactions.domain.model")
class TransactionApplication


fun main(args: Array<String>) {
    runApplication<TransactionApplication>(*args)
}