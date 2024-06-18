package com.example.example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.example.ui.theme.ExampleTheme
import io.tbib.composequill.QuillEditor
import io.tbib.composequill.QuillStyle
import io.tbib.composequill.components.QuillEditorToolBarStyle
import io.tbib.composequill.states.rememberQuillStates


//import com.tbib.composequill.components.TextRichToolBarStyle

class MainActivity : ComponentActivity() {
    private val newData =
        """
[{"type":"TEXT","value":"<p>sds <b>sds </b></p><br><br><p><b>1<sup>2 H<sub>2</sub>O </sup></b>7<sup>3 <b>fg</b></sup> hg</p>"},{"type":"IMAGE"},{"type":"VIDEO"}] """.trimIndent()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            val quillStates = rememberQuillStates()
            quillStates.SetCache()

            quillStates.sendData(newData)
            quillStates.useGoogleFont("AIzaSyA6F2ql0igVrS1e-N2lLKKAbfSeJz8fJJk", this)
            ExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.background(Color.White)) {


                        //quillStates.init(newData)
                        QuillEditor(
                            modifier = Modifier
                                .padding(0.dp)
                                .background(Color.White),
                            quillEditorToolBarStyle = QuillEditorToolBarStyle(
                                iconColor = Color.Black,
                                iconSelectedColor = Color.White,
                            ),
                            showImagePicker =   true,
                            showVideoPicker = true,
                            quillStyle = QuillStyle(
                                modifier = Modifier
                                    .background(Color.Red)
                                    .padding(10.dp)
                                    .clip(shape = RoundedCornerShape(20.dp)),
                            ),
                            quillStates = quillStates,
                            onChange = {
                                Log.d("QuillEditor", "onCreate: $it")
                            })

                    }
                }
            }
        }
    }
}

