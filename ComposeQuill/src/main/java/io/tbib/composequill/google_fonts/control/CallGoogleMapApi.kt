package io.tbib.composequill.google_fonts.control

import android.annotation.SuppressLint
import io.tbib.composequill.google_fonts.api.GoogleFontsItemsModel
import io.tbib.composequill.google_fonts.api.GoogleFontsModel
import io.tbib.composequill.google_fonts.api.GoogleMapApi
import io.tbib.composequill.objet_box.SaveFont
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class CallGoogleMapApi {
    @SuppressLint("SuspiciousIndentation")
    fun getGoogleFont(apiKey: String): List<GoogleFontsItemsModel> {
        val fontBox = SaveFont.get().boxFor(GoogleFontsItemsModel::class.java)

        var fonts: List<GoogleFontsItemsModel>? = fontBox.all
        if (fonts.isNullOrEmpty()) {
            val api = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GoogleMapApi::class.java)
            api.getGoogleFont(apiKey).enqueue(object : Callback<GoogleFontsModel> {
                override fun onResponse(
                    call: retrofit2.Call<GoogleFontsModel>,
                    response: retrofit2.Response<GoogleFontsModel>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        fonts = data!!.items
                        fontBox.put(fonts!!)

                    }

                }

                override fun onFailure(call: retrofit2.Call<GoogleFontsModel>, t: Throwable) {
                    println(t.message)
                }
            })

        }
        return fonts!!

    }
}