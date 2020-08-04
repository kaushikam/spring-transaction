package com.kaushikam.transactions.`interface`.web.rest.exception

class ApiValidationError(
    val objName: String,
    val field: String? = null,
    val rejectedValue: Any? = null,
    val message: String
): ApiSubError()