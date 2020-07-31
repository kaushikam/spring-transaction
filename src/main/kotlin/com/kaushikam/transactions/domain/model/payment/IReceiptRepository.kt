package com.kaushikam.transactions.domain.model.payment

interface IReceiptRepository {
    fun save(receipt: Receipt)
}