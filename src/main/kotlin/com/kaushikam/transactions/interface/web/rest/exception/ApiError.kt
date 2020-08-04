package com.kaushikam.transactions.`interface`.web.rest.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

sealed class ApiError(
    val httpStatus: HttpStatus? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now(),
    open val errorCode: Int,
    open val message: String? = null,
    val debugMessage: String? = null,
    val subErrors: MutableList<ApiSubError> = mutableListOf()
) {
    constructor(status: HttpStatus, errorCode: Int): this(httpStatus = status, errorCode = errorCode)
    constructor(status: HttpStatus, errorCode: Int, cause: Throwable): this(httpStatus = status, errorCode = errorCode, message = "Unexpected error", debugMessage = cause.localizedMessage)
    constructor(status: HttpStatus, errorCode: Int, message: String, cause: Throwable): this(httpStatus = status, errorCode = errorCode, message = message, debugMessage = cause.localizedMessage)

    data class NotFound(override val message: String, override val errorCode: Int): ApiError(httpStatus = HttpStatus.NOT_FOUND, message = message, errorCode = errorCode)
    data class BadRequest(override val message: String, override val errorCode: Int): ApiError(httpStatus = HttpStatus.BAD_REQUEST, message = message, errorCode = errorCode)
}