package com.tbib.composequill.components

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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState


@Composable
internal fun BuildQuillWithImage(
    image: ImageBitmap?,
    maxHeight: Dp,
    state: RichTextState,
    readOnly: Boolean,
) {
    val height = if (image!!.height > 200) 200 else image.height

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
                QuillEditorBuilder(state, readOnly)
                Image(
                    modifier = Modifier.height(height.dp),
                    bitmap = image,
                    contentDescription = "contentDescription"
                )
            }
        }
    }
}
