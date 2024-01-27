package io.tbib.composequill.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichText

data class QuillEditorStyle(
    val minLines: Int = 5,
    val maxLines: Int = 10,
    val enable: Boolean = true,
    val singleLine: Boolean = false,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
    val textStyle: TextStyle = TextStyle(fontSize = 30.sp),
    val cursorBrush: Brush = SolidColor(Color.Black),
    val decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() },
    val modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),


    )

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun QuillEditorBuilder(
    style: QuillEditorStyle, state: RichTextState, readOnly: Boolean,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    if (!readOnly)
        BasicRichTextEditor(
            modifier = style.modifier,
            state = state,
            minLines = style.minLines,
            textStyle = TextStyle(fontSize = 30.sp),
            maxLines = style.maxLines,
            enabled = style.enable,
            keyboardOptions = style.keyboardOptions,
            keyboardActions = style.keyboardActions,
            onTextLayout = onTextLayout,
            interactionSource = interactionSource,
            cursorBrush = style.cursorBrush,
            decorationBox = style.decorationBox,

            )
    else
        RichText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            state = state,
            fontSize = 30.sp,
        )
}