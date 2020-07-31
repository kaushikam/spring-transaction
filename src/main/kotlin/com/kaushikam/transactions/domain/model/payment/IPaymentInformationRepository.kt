package com.kaushikam.transactions.domain.model.payment

interface IPaymentInformationRepository {
    fun save(information: PaymentInformation)
    fun findById(id: Long): PaymentInformation?
    fun findAll(): List<PaymentInformation>
}