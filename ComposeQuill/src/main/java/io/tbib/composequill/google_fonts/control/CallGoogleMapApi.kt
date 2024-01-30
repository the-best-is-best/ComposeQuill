package io.tbib.composequill.google_fonts.control

import android.annotation.SuppressLint
import androidx.compose.ui.text.googlefonts.GoogleFont
import io.tbib.composequill.google_fonts.api.GoogleFontsItemsModel
import io.tbib.composequill.google_fonts.api.GoogleMapApi
import io.tbib.composequill.objet_box.SaveGoogleFont
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class CallGoogleMapApi {
    @SuppressLint("SuspiciousIndentation")
    suspend fun getGoogleFont(apiKey: String): List<GoogleFont?> {
        val fontBox = SaveGoogleFont.get().boxFor(GoogleFontsItemsModel::class.java)

        var fonts: List<GoogleFontsItemsModel>? = null
        val fontGoogle: MutableList<GoogleFont?> = mutableListOf()

        val api = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

            .create(GoogleMapApi::class.java)
        val response = api.getGoogleFont(apiKey)
        if (response.isSuccessful) {
            val data = response.body()
            fonts = data!!.items
            fontBox.put(fonts)

        }

        fonts?.forEach { fontGoogle.add(GoogleFont(it.family)) }
        return fontGoogle

    }
}