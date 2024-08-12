package ktor.ktor_handle_errors

data class Failure(
    val statusCode: Int? = null,
    val messages: String? = null
)