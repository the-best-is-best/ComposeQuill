package io.tbib.composequill.services

import android.util.Base64
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


internal fun convertBase64ToImage(
    base64String: String,
    path: String,
    callback: (File) -> Unit
) {

    val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
    val videoFile = File(path, "image_quill.png")
    try {
        FileOutputStream(videoFile).use { outputStream ->
            outputStream.write(decodedBytes)
        }
        callback(videoFile)
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
