package com.tbib.composequill

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbib.composequill.enum.QuillType
import com.tbib.composequill.models.QuillParser
import com.tbib.composequill.services.convertFileToBase64
import com.tbib.composequill.services.imageBitmapToJsonString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

internal class QuillViewModel(json: String) : ViewModel() {
    var textRich = MutableLiveData<String>("")
    var loading = MutableLiveData<Boolean>(false)
    val image = MutableLiveData<
            ImageBitmap?>(null)

    val video = MutableLiveData<
            String?>(null)

    init {
        viewModelScope.launch {
            if (json.isNotEmpty()) {
                val data: List<QuillParser> = Json.decodeFromString<List<QuillParser>>(json)
                for (item in data) {
                    when (item.type) {
                        QuillType.TEXT -> {
                            textRich.value = item.value ?: ""
                        }

                        QuillType.IMAGE -> {
                            if (item.value.isNullOrEmpty()) continue
                            val decodedString = Base64.decode(item.value, Base64.DEFAULT)
                            val decodedBitmap =
                                BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                            image.value = decodedBitmap.asImageBitmap()
                        }

                        QuillType.VIDEO -> {
                            if (item.value.isNullOrEmpty()) continue
                            video.value = item.value
                        }
                    }
                }
            }
//              delay(500)
//                textRich.value = ""
        }


    }


    fun addImage(newImage: String) {
        if (newImage.isEmpty()) return
        if (newImage == image.value?.let { imageBitmapToJsonString(it) }) return
        val base64 = convertFileToBase64(newImage)
        val decodedString = Base64.decode(base64, Base64.DEFAULT)
        val decodedBitmap =
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        image.value = decodedBitmap.asImageBitmap()
        video.value = null

    }

    fun addVideo(newVideo: String) {
        if (newVideo.isEmpty()) return
        if (convertFileToBase64(newVideo) == video.value) return
        viewModelScope.launch {
            loading.value = true
            image.value = null
            video.value = null
            delay(1000)
            val base64 = convertFileToBase64(newVideo)
            video.value = base64
        }
    }
}

