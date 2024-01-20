package com.tbib.composequill

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.tbib.composequill.components.BuildQuillOnly
import com.tbib.composequill.components.BuildQuillWithImage
import com.tbib.composequill.components.BuildQuillWithVideo
import com.tbib.composequill.components.TextRichToolBar
import com.tbib.composequill.components.TextRichToolBarStyle
import com.tbib.composequill.enum.QuillType
import com.tbib.composequill.models.QuillParser
import com.tbib.composequill.services.imageBitmapToJsonString
import com.tbib.composequill.services.rememberImeState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class QuillEditorStyle(
    val modifier: Modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
)

@SuppressLint("RememberReturnType", "StateFlowValueCalledInComposition")
@Composable
fun QuillEditor(
    modifier: Modifier = Modifier,
    quillEditorStyle: QuillEditorStyle = QuillEditorStyle(),
    value: String? = null,
    readOnly: Boolean = false,
    onChange: (value: String) -> Unit,
    textRichToolBarStyle: TextRichToolBarStyle = TextRichToolBarStyle()
) {
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val loadPage = remember { mutableStateOf(false) }


    var newText by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val maxHeight = (screenHeight / 2)


    val state: RichTextState = rememberRichTextState()
    val viewModel: QuillViewModel = remember {
        QuillViewModel(value ?: "")
    }
    val image: ImageBitmap? by viewModel.image.observeAsState()

    val video: String? by viewModel.video.observeAsState()

    if (!loadPage.value) {
        loadPage.value = true
        state.setHtml(viewModel.textRich.value!!)
    }
    if (state.toHtml() != newText) {
        newText = state.toHtml()
        viewModel.textRich.value = newText
        state.setHtml(newText)
    }
    LaunchedEffect(key1 = imeState.value) {
        scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        onChange(
            toJson(newText, image, video)
        )

    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 10.dp
                ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!readOnly) {
                TextRichToolBar(
                    viewModel, state, onChange = {
                        onChange(toJson(newText, image, video))
                    },
                    style = textRichToolBarStyle
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
            Box(modifier = quillEditorStyle.modifier) {
                if (image == null && video == null) {

                    BuildQuillOnly(maxHeight, state, readOnly)

                } else if (image != null) {
                    BuildQuillWithImage(image, maxHeight, state, readOnly)
                } else if (video != null) {
                    BuildQuillWithVideo(viewModel, maxHeight, state, readOnly)
                }
            }
        }


    }

}


private fun toJson(
    newText: String,
    image: ImageBitmap?,
    video: String?
) = Json.encodeToString(
    listOf(
        QuillParser(
            type = QuillType.TEXT,
            value = newText
        ),
        QuillParser(
            type = QuillType.IMAGE,
            value = if (image == null) null else imageBitmapToJsonString(image)
        ),
        QuillParser(
            type = QuillType.VIDEO,
            value = video
        )
    )
)
