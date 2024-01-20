package com.example.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.example.ui.theme.ExampleTheme
import com.tbib.composequill.QuillEditor
import com.tbib.composequill.components.TextRichToolBarStyle

class MainActivity : ComponentActivity() {


    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.background(Color.White)) {


                        QuillEditor(
                            modifier = Modifier
                                .padding(0.dp)
                                .background(Color.White),
                            textRichToolBarStyle = TextRichToolBarStyle(
                                iconColor = Color.Black,
                                iconSelectedColor = Color.White,

                                ),

                            value = null, onChange = {
                                Log.d("QuillEditor", "onCreate: $it")
                            })
                        Spacer(modifier = Modifier.height(20.dp))
                        ElevatedButton(onClick = { }, modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Save",
                                style = TextStyle(fontSize = 20.sp, color = Color.Red)
                            )
                        }
                    }
                }
            }
        }
    }
}

