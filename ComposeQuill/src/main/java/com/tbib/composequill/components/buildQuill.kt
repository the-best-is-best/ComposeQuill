package com.tbib.composequill.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichText


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun QuillEditorBuilder(state: RichTextState, readOnly: Boolean) {
    if (!readOnly)
        BasicRichTextEditor(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            state = state,
            minLines = 5,
            textStyle = TextStyle(fontSize = 30.sp),
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