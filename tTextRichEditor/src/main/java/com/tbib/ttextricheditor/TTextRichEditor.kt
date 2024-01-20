package com.tbib.ttextricheditor

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor
import com.tbib.ttextricheditor.components.TextRichToolBar
import com.tbib.ttextricheditor.services.convertFileToBase64

@Composable
fun TTextRichEditor() {
    val state = rememberRichTextState()

    val image = remember {
        mutableStateMapOf<String, String?>()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(
                horizontal = 20.dp
            ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextRichToolBar(
                state,
                imageSelected = { path, name ->
                    if (path.isNotEmpty()) {
                        image["name"] = name
                        image["path"] = path
                        image["base64"] = convertFileToBase64(image["path"]!!)
                    }

                },
                videoSelected = { path, name ->

                },

                )

            Spacer(modifier = Modifier.height(10.dp))
            if (image.isEmpty()) {
                Box(
                    modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    BasicRichTextEditor(
                        state,
                        minLines = 5,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 30.sp)
                    )
                }
            } else if (image.isNotEmpty()) {
                val renderImage = image["base64"]
                val decodedString = Base64.decode(renderImage, Base64.DEFAULT)
                val decodedBitmap =
                    BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                val height = if (decodedBitmap.height > 200) 200 else decodedBitmap.height
                Box(
                    modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BasicRichTextEditor(
                            state,
                            minLines = 5,
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(fontSize = 30.sp)
                        )
                        Image(
                            modifier = Modifier.height(height.dp),
                            bitmap = decodedBitmap.asImageBitmap(),

                            contentDescription = "contentDescription"
                        )
                    }
                }
            }

        }
    }
}
//
//@Composable
//fun TTextRich(state: RichTextState){
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        RichTextEditor(state , readOnly = true)
//    }
//}