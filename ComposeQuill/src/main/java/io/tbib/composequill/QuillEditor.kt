package io.tbib.composequill

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import io.tbib.composequill.components.BuildQuillOnly
import io.tbib.composequill.components.BuildQuillWithImage
import io.tbib.composequill.components.BuildQuillWithVideo
import io.tbib.composequill.components.QuillEditorStyle
import io.tbib.composequill.components.QuillEditorToolBar
import io.tbib.composequill.components.QuillEditorToolBarStyle
import io.tbib.composequill.components.styles.DialogStyle
import io.tbib.composequill.enum.QuillType
import io.tbib.composequill.extensions.fixHtml
import io.tbib.composequill.models.QuillParser
import io.tbib.composequill.services.convertFileToBase64
import io.tbib.composequill.services.rememberImeState
import io.tbib.composequill.states.QuillStates
import io.tbib.composequill.states.rememberQuillStates
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class QuillStyle(
    val modifier: Modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(10.dp))

)

@JvmOverloads
@SuppressLint("RememberReturnType", "StateFlowValueCalledInComposition")
@Composable
fun QuillEditor(
    modifier: Modifier = Modifier,
    quillStyle: QuillStyle = QuillStyle(),
    quillEditorStyle: QuillEditorStyle = QuillEditorStyle(),
    readOnly: Boolean = false,
    onChange: (value: String) -> Unit,
    quillEditorToolBarStyle: QuillEditorToolBarStyle = QuillEditorToolBarStyle(),
    quillStates: QuillStates = rememberQuillStates(),
    showImagePicker: Boolean = true,
    showVideoPicker: Boolean = true,
    colorPickerDialogStyle: DialogStyle = DialogStyle(),
) {
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()


//    var newText by rememberSaveable { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val maxHeight = (screenHeight / 2)


if(!quillStates.keyApiGoogle .isNullOrEmpty()){
    quillStates.getFonts()
}

    LaunchedEffect(key1 = imeState.value) {
        scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        onChange(
            toJson(
                quillStates.textState.toHtml().fixHtml(),
                if (quillStates.image == null) null else convertFileToBase64(quillStates.image!!),
                if (quillStates.video == null) null else convertFileToBase64(quillStates.video!!)
            )
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
                QuillEditorToolBar(
                    showImagePicker,
                    showVideoPicker,

                    quillStates,
                    onChange = {
                        onChange(
                            toJson(
                                quillStates.textState.toHtml().fixHtml(),
                                if (quillStates.image == null) null else convertFileToBase64(
                                    quillStates.image!!
                                ),
                                if (quillStates.video == null) null else convertFileToBase64(
                                    quillStates.video!!
                                )
                            )
                        )
                    },
                    style = quillEditorToolBarStyle,
                    dialogStyle = colorPickerDialogStyle,

                    )

                Spacer(modifier = Modifier.height(10.dp))
            }
            Box(modifier = quillStyle.modifier) {
                if (quillStates.image == null && quillStates.video == null) {

                    BuildQuillOnly(maxHeight, quillStates.textState, readOnly, quillEditorStyle)

                } else if (quillStates.image != null) {
                    BuildQuillWithImage(maxHeight, quillStates, readOnly, quillEditorStyle)
                } else if (quillStates.video != null) {
                    BuildQuillWithVideo(maxHeight, quillStates, readOnly, quillEditorStyle)
                }
            }
        }


    }

}


private fun toJson(
    newText: String,
    image: String?,
    video: String?
) = Json.encodeToString(
    listOf(
        QuillParser(
            type = QuillType.TEXT,
            value = newText
        ),
        QuillParser(
            type = QuillType.IMAGE,
            value = image
        ),
        QuillParser(
            type = QuillType.VIDEO,
            value = video
        )
    )
)
