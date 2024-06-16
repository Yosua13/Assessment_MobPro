package org.d3if0097assessment1.model

data class OpStatus<T>(
    val statusCode: Int,
    val data: T,
    val message: String?
)