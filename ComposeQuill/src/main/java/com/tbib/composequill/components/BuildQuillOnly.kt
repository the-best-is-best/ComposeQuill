package com.tbib.composequill.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.mohamedrejeb.richeditor.model.RichTextState


@Composable
internal fun BuildQuillOnly(
    maxHeight: Dp,
    state: RichTextState,
    readOnly: Boolean,
    style: RichTextEditorStyle
) {

    LazyColumn(
        modifier = Modifier
            .heightIn(max = maxHeight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            QuillEditorBuilder(style, state, readOnly)
        }
    }

}
