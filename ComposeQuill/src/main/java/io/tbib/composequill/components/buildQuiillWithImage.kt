package io.tbib.composequill.components

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.tbib.composequill.states.QuillStates


@Composable
internal fun BuildQuillWithImage(

    maxHeight: Dp,
    state: QuillStates,
    readOnly: Boolean,
    style: RichTextEditorStyle
) {
    val decodedString = Base64.decode(state.image, Base64.DEFAULT)
    val decodedBitmap =
        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    val image = decodedBitmap.asImageBitmap()
    val height = if (image.height > 200) 200 else image.height

    Box(
        modifier = Modifier
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .heightIn(max = maxHeight),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                QuillEditorBuilder(style, state.textState, readOnly)
                Image(
                    modifier = Modifier.height(height.dp),
                    bitmap = image,
                    contentDescription = "contentDescription"
                )
            }
        }
    }
}
