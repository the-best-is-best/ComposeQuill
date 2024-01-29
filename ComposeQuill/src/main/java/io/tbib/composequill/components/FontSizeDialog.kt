package io.tbib.composequill.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.ColorPickerController
import io.tbib.composequill.components.styles.DialogStyle

internal enum class ENUMFontSize {
    SMALL,
    NORMAL,
    MEDIUM,
    LARGE,
    XLARGE
}

internal fun ENUMFontSize.toFontSize() = run {
    when (this) {
        ENUMFontSize.SMALL -> 15.sp
        ENUMFontSize.NORMAL -> 20.sp
        ENUMFontSize.MEDIUM -> 25.sp
        ENUMFontSize.LARGE -> 35.sp
        ENUMFontSize.XLARGE -> 50.sp
    }
}

internal fun TextUnit.toENUM(value: TextUnit) = run {
    when (value) {
        15.sp -> ENUMFontSize.SMALL
        20.sp -> ENUMFontSize.NORMAL
        25.sp -> ENUMFontSize.MEDIUM
        35.sp -> ENUMFontSize.LARGE
        50.sp -> ENUMFontSize.XLARGE
        else -> ENUMFontSize.NORMAL
    }
}

@Composable
internal fun FontSizeDialog(
    style: DialogStyle = DialogStyle(),
    onDismissRequest: () -> Unit,
    controller: ColorPickerController,
    onChange: (value: ENUMFontSize) -> Unit,
    initSize: ENUMFontSize? = null
) {
    Dialog(onDismissRequest = onDismissRequest) {

        // Custom shape, background, and layout for the dialog
        Surface(
            modifier = style.dialogStyle,
            shape = RoundedCornerShape((style.roundedCorner ?: 16).dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var size by remember {
                    mutableStateOf(initSize ?: ENUMFontSize.NORMAL)
                }
                ENUMFontSize.entries.forEach {
                    val isSelected = it == size
                    val color = if (isSelected) Color.Blue else Color.Black
                    val fontSize = it.toFontSize()
                    Text(
                        text = it.name,
                        fontSize = fontSize,
                        color = color,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                size = it
                                onChange(it)
                            }
                    )
                }
            }
        }
    }
}