package com.example.example

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.example.ui.theme.ExampleTheme
import io.tbib.composequill.QuillEditor
import io.tbib.composequill.QuillStyle
import io.tbib.composequill.components.QuillEditorToolBarStyle
import io.tbib.composequill.states.rememberQuillStates
import java.io.IOException

fun readJsonFileFromAssets(context: Context, fileName: String): String {
    return try {
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charsets.UTF_8)
    } catch (ex: IOException) {
        ex.printStackTrace()
        ""
    }
}

//import com.tbib.composequill.components.TextRichToolBarStyle

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            val quillStates = rememberQuillStates()
            quillStates.SetCache()
            val context = LocalContext.current
            val newData = readJsonFileFromAssets(context, "data.json")

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

