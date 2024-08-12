package ktor.ktor_services

import GoogleFonts
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import ktor.KtorApi

internal class KtorServices : KtorApi() {

    suspend fun getGoogleFonts(apiKey: String): GoogleFonts = client.post {
        pathUrl("/webfonts/v1/webfonts")
        parameter("key", apiKey)

    }.body()
}
