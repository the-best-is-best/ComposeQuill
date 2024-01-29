package io.tbib.composequill.states

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.mohamedrejeb.richeditor.model.RichTextState
import io.tbib.composequill.components.ENUMFontSize
import io.tbib.composequill.components.toFontSize
import io.tbib.composequill.enum.QuillType
import io.tbib.composequill.google_fonts.control.CallGoogleMapApi
import io.tbib.composequill.models.QuillParser
import io.tbib.composequill.objet_box.SaveFont
import io.tbib.composequill.services.convertFileToBase64
import kotlinx.serialization.json.Json

@Composable
fun rememberQuillStates(): QuillStates {
    return rememberSaveable(saver = QuillStates.Saver) {
        QuillStates()
    }
}

class QuillStates internal constructor(
    textState: RichTextState
) {
    internal constructor() : this(RichTextState())

    internal var textState by mutableStateOf(textState)
    internal var loading by mutableStateOf(false)
    internal var image by mutableStateOf<
            String?>(null)
    internal var video by mutableStateOf<
            String?>(null)
    private var isInit by mutableStateOf(false)
    internal var fonts by mutableStateOf<List<GoogleFont>?>(null)
    private var keyApiGoogle: String? = null
//    internal var fontSize: ENUMFontSize by mutableStateOf(ENUMFontSize.NORMAL)


    fun sendData(json: String) {
        if (isInit) return
        val data: List<QuillParser> = Json.decodeFromString<List<QuillParser>>(json)
        for (item in data) {
            when (item.type) {
                QuillType.TEXT -> {
                    textState.setHtml(item.value.toString())
                }

                QuillType.IMAGE -> {
                    if (item.value.isNullOrEmpty()) continue
//                    val decodedString = Base64.decode(item.value, Base64.DEFAULT)
//                    val decodedBitmap =
//                        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    image = item.value
                }

                QuillType.VIDEO -> {
                    if (item.value.isNullOrEmpty()) continue
                    video = item.value
                }
            }

        }

        isInit = true

    }

    internal fun changeTextColor(color: Color) {
        textState.toggleSpanStyle(
            SpanStyle(
                color = color
            )
        )
    }

    internal fun changeTextBackgroundColor(color: Color) {
        textState.toggleSpanStyle(
            SpanStyle(
                background = color
            )
        )
    }

    internal fun changeFont(font: FontFamily) {
        textState.toggleSpanStyle(
            SpanStyle(
                fontFamily = font
            )
        )
    }

    internal fun changeFontSize(size: ENUMFontSize) {
        textState.toggleSpanStyle(
            SpanStyle(
                fontSize = size.toFontSize()
            )
        )
        //fontSize = size
    }

    internal fun addImage(newImage: String) {
        if (newImage.isEmpty()) return
        if (newImage == image) return
        val base64 = convertFileToBase64(newImage)
//        val decodedString = Base64.decode(base64, Base64.DEFAULT)
//        val decodedBitmap =
//            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        image = base64
        video = null

    }

    internal fun addVideo(newVideo: String) {
        if (newVideo.isEmpty()) return
        if (convertFileToBase64(newVideo) == video) return
        loading = true
        image = null
        val base64 = convertFileToBase64(newVideo)
        video = base64


    }

    fun clear() {
        textState = RichTextState()
        image = null
        video = null
        loading = false

    }

    fun useGoogleFont(key: String, context: Context) {
        if (keyApiGoogle == key) return
        keyApiGoogle = key
        SaveFont.init(context)
        getFonts()

    }

    private fun getFonts() {
        if (keyApiGoogle == null) return

        fonts = CallGoogleMapApi().getGoogleFont(keyApiGoogle!!)

    }

    companion object {
        val Saver: Saver<QuillStates, *> = listSaver(
            save = {
                listOf(
                    it.loading,
                    it.image,
                    it.video,
                    it.textState.toHtml(),
                    it.isInit,
                    it.keyApiGoogle,
//                    it.fontSize,
                    it.fonts
                )
            },

            restore = {
                val loading = it[0] as Boolean
                val image = it[1] as String?
                val video = it[2] as String?
                val textState = it[3].toString()


                val quillStates = QuillStates()
                quillStates.loading = loading
                quillStates.image = image
                quillStates.video = video
                quillStates.textState.setHtml(textState)
                quillStates.isInit = it[4] as Boolean
                quillStates.keyApiGoogle = it[5] as String?
//                quillStates.fontSize =  it[6] as ENUMFontSize
                quillStates.fonts = it[6] as List<GoogleFont>?
                quillStates
            }
        )
    }
}