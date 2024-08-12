package states

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
import androidx.compose.ui.text.style.BaselineShift
import com.mohamedrejeb.richeditor.model.RichTextState
import database.GoogleFonts
import enum.QuillType
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import models.QuillParser
import utils.Base64Converter
import utils.getCachePath

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
    internal var fonts by mutableStateOf<List<GoogleFonts?>>(listOf())

    var keyApiGoogle: String? = null


    private val cachePath = getCachePath()


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

                    Base64Converter().convertBase64ToImage(item.value) { file ->
                        image = file.path
                    }
                }

                QuillType.VIDEO -> {
                    if (item.value.isNullOrEmpty()) continue
                    convertBase64ToVideo(item.value, cachePath) { file ->
                        video = file.absolutePath
                    }
                }
            }

        }

        isInit = true

    }

    internal fun superScript() {
        resetScript()
        textState.toggleSpanStyle(
            SpanStyle(
                fontSize = textState.currentSpanStyle.fontSize,
                baselineShift = BaselineShift.Superscript
            )
        )
    }

    internal fun subScript() {
        resetScript()
        textState.toggleSpanStyle(
            SpanStyle(
                fontSize = textState.currentSpanStyle.fontSize,
                baselineShift = BaselineShift.Subscript
            )
        )
    }

    internal fun resetScript() {
        if (textState.currentSpanStyle.baselineShift == BaselineShift.Subscript) {
            textState.toggleSpanStyle(
                SpanStyle(
                    fontSize = textState.currentSpanStyle.fontSize,
                    baselineShift = BaselineShift(0.5000001f)
                )
            )
        } else if (textState.currentSpanStyle.baselineShift == BaselineShift.Superscript) {
            textState.toggleSpanStyle(
                SpanStyle(
                    fontSize = textState.currentSpanStyle.fontSize,
                    baselineShift = BaselineShift(-0.5000001f)
                )
            )
        }
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
//        val decodedString = Base64.decode(base64, Base64.DEFAULT)
//        val decodedBitmap =
//            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        image = newImage
        video = null

    }

    internal fun addVideo(newVideo: String) {
        if (newVideo.isEmpty()) return
        if (newVideo == video) return
        loading = true
        image = null
        video = newVideo


    }

    fun clear() {
        textState = RichTextState()
        image = null
        video = null
        loading = false

    }

    fun useGoogleFont(key: String, context: Context) {
        if (keyApiGoogle == key) return

        SaveGoogleFont.init(context)
        keyApiGoogle = key
        getFonts()

    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getFonts() {
        if (keyApiGoogle == null) return
        val fontBox = SaveGoogleFont.get().boxFor(GoogleFontsItemsModel::class.java)
        val getFonts = fontBox.all
        if (getFonts.isNullOrEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                fonts = CallGoogleMapApi().getGoogleFont(keyApiGoogle!!)
            }
        } else {
            if (getFonts.isNotEmpty()) {
                fonts = getFonts.map { GoogleFont(it.family) }
            }
        }


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
//                  it.fonts
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
//                quillStates.fonts = (it[6] as List<String>).map { e -> GoogleFont(e) }
                quillStates
            }
        )
    }
}