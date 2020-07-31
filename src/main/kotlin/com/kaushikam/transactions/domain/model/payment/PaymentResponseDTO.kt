package com.kaushikam.transactions.domain.model.payment

import java.time.LocalDateTime

data class PaymentResponseDTO(
    val amount: Double,
    val transactionTime: LocalDateTime
)