package com.kaushikam.transactions.common.exception

enum class ErrorCode(
    val code: Int,
    val errorName: String
) {
    PAYMENT_INFORMATION_NOT_FOUND(1, "PaymentInformation.notFound"),
    VALIDATION_ERROR(2, "validationError")
}