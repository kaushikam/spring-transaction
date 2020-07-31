package com.kaushikam.transactions.`interface`.web.rest.exception

import com.kaushikam.transactions.domain.model.payment.MaximumAttemptsExceededException
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

private val eLogger = KotlinLogging.logger {}

@RestControllerAdvice
class PaymentExceptionHandler(): ResponseEntityExceptionHandler() {
    @ExceptionHandler(MaximumAttemptsExceededException::class)
    fun handleMaximumAttemptsExceededException(
        e: MaximumAttemptsExceededException, r: WebRequest
    ): ResponseEntity<Any> {
        eLogger.error { e }
        return handleExceptionInternal(e, e.message, HttpHeaders(), HttpStatus.FORBIDDEN, r)
    }
}