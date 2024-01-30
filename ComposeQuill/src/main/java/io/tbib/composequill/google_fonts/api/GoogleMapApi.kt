package io.tbib.composequill.google_fonts.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GoogleMapApi {

    @GET("webfonts/v1/webfonts")
    suspend fun getGoogleFont(@Query("key") apiKey: String): Response<GoogleFontsModel>

}