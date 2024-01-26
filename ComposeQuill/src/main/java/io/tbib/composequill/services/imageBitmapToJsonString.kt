package io.tbib.composequill.services

import android.graphics.Bitmap
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.ByteArrayOutputStream

internal fun imageBitmapToJsonString(bitmap: ImageBitmap): String {
    val stream = ByteArrayOutputStream()
    bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream)
    val byteArray = stream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}