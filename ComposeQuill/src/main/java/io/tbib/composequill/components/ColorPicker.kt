package io.tbib.composequill.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker


class ColorPickerDialogStyle(
    val buttonStyle: Modifier = Modifier,

    )

@SuppressLint("UnrememberedMutableState")
@Composable
internal fun ColorPickerDialog(
    style: ColorPickerDialogStyle = ColorPickerDialogStyle(),
    onDismissRequest: () -> Unit,
    controller: ColorPickerController,
    onChange: (value: Color) -> Unit,
    initColor: Color
) {
    Dialog(onDismissRequest = onDismissRequest) {
        var color by remember {
            mutableStateOf(initColor)
        }
        // Custom shape, background, and layout for the dialog
        Surface(
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HsvColorPicker(
                    initialColor = initColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                        .padding(10.dp),
                    controller = controller,
                    onColorChanged = {
                        color = it.color
                    },

                    )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    ElevatedButton(
                        modifier = ColorPickerDialogStyle().buttonStyle,
                        onClick = {
                            onDismissRequest()

                        }) {
                        Text(text = "Cancel")

                    }

                    ElevatedButton(
                        modifier = style.buttonStyle,
                        onClick = {
                            onChange(color)
                            onDismissRequest()
                        }) {
                        Text(text = "Ok")


                    }

                }
            }
        }
    }
}

