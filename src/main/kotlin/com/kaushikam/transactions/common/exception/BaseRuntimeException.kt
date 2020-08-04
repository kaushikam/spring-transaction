package com.kaushikam.transactions.common.exception

import org.springframework.context.MessageSource

abstract class BaseRuntimeException(
    override val message: String,
    open val errorCode: ErrorCode,
    open val args: Array<Any>,
    override val cause: Throwable?
): RuntimeException(message, cause)