package com.kaushikam.transactions.`interface`.web.rest.exception

import com.kaushikam.transactions.application.impl.PaymentInformationNotFoundException
import com.kaushikam.transactions.common.exception.BaseRuntimeException
import com.kaushikam.transactions.common.exception.ErrorCode
import com.kaushikam.transactions.domain.model.payment.MaximumAttemptsExceededException
import mu.KotlinLogging
import org.springframework.context.MessageSource
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import javax.validation.ConstraintViolationException

private val eLogger = KotlinLogging.logger {}

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class PaymentExceptionHandler(
    val messageSource: MessageSource,
    val locale: Locale
): ResponseEntityExceptionHandler() {
    @ExceptionHandler(MaximumAttemptsExceededException::class)
    fun handleMaximumAttemptsExceededException(
        e: MaximumAttemptsExceededException, r: WebRequest
    ): ResponseEntity<Any> {
        eLogger.error { e }
        return handleExceptionInternal(e, e.message, HttpHeaders(), HttpStatus.FORBIDDEN, r)
    }

    @ExceptionHandler(PaymentInformationNotFoundException::class)
    fun handleNotFoundException(e: BaseRuntimeException, request: WebRequest): ResponseEntity<Any> {
        eLogger.error { "Inside not found" }
        val message = getLocalizedMessage(e)
        return buildResponseEntity(ApiError.NotFound(errorCode = e.errorCode.code, message = message))
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError.BadRequest("Validation error in input", ErrorCode.VALIDATION_ERROR.code)
        ex.bindingResult.fieldErrors.map {
            val errorMessage = messageSource.getMessage(it, locale)
            ApiValidationError(it.objectName, it.field, it.rejectedValue, errorMessage)
        }.forEach {
            apiError.subErrors.add(it)
        }
        return buildResponseEntity(apiError)
    }

    private fun buildResponseEntity(apiError: ApiError): ResponseEntity<Any> {
        return ResponseEntity(apiError, apiError.httpStatus!!)
    }

    private fun getLocalizedMessage(e: BaseRuntimeException): String {
        return messageSource.getMessage(e.errorCode.errorName, e.args, locale)
    }
}