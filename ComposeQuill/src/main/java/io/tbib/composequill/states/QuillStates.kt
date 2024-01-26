package io.tbib.composequill.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.mohamedrejeb.richeditor.model.RichTextState
import io.tbib.composequill.enum.QuillType
import io.tbib.composequill.models.QuillParser
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

    fun init(json: String) {
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

    fun addImage(newImage: String) {
        if (newImage.isEmpty()) return
        if (newImage == image) return
        val base64 = convertFileToBase64(newImage)
//        val decodedString = Base64.decode(base64, Base64.DEFAULT)
//        val decodedBitmap =
//            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        image = base64
        video = null

    }

    fun addVideo(newVideo: String) {
        if (newVideo.isEmpty()) return
        if (convertFileToBase64(newVideo) == video) return
        loading = true
        image = null
        val base64 = convertFileToBase64(newVideo)
        video = base64


    }

    companion object {
        val Saver: Saver<QuillStates, *> = listSaver(
            save = {
                listOf(
                    it.loading,
                    it.image,
                    it.video,
                    it.textState.toHtml(),
                    it.isInit
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
                quillStates
            }
        )
    }
}