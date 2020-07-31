package com.kaushikam.transactions.domain.model.payment

enum class PaymentStatus {
    INITIATED,
    RESPONSE_CAME,
    SUCCESS,
    FAILURE;

    fun isFinished(): Boolean {
        return this == SUCCESS || this == FAILURE
    }
}