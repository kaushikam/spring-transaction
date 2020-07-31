package com.kaushikam.transactions.application

import com.kaushikam.transactions.domain.model.payment.PaymentInformation
import com.kaushikam.transactions.domain.model.payment.PaymentRequestDTO
import com.kaushikam.transactions.domain.model.payment.PaymentResponseDTO
import java.time.LocalDateTime

interface IPaymentService {
    fun placeOrder(paymentRequest: PaymentRequestDTO, requestTime: LocalDateTime): PaymentInformation
    fun makePayment(paymentInformation: PaymentInformation, receiptCreationTime: LocalDateTime): PaymentInformation
    fun savePaymentResponse(informationId: Long, paymentResponse: PaymentResponseDTO, responseTime: LocalDateTime): PaymentInformation
    fun listAllInformation(): List<PaymentInformation>
}