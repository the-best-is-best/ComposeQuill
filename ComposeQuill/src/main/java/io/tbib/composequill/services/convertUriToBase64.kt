package io.tbib.composequill.services

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream

@SuppressLint("Recycle")
internal fun convertUriToBase64(context:Context, uri: Uri): String? {
    try {
//
//    val extension = MimeTypeMap.getSingleton()
//        .getExtensionFromMimeType(context.contentResolver.getType(uri))

        val inputStream = context.contentResolver.openInputStream(uri)

        val bytes = readBytesFromInputStream(inputStream!!)
        val byteArrayOutputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var bytesRead: Int
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead)
        }

        return Base64.encodeToString(bytes, Base64.DEFAULT)
    } catch (e: Exception) {
        return null
    }
}

private

fun readBytesFromInputStream(inputStream: InputStream): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val buffer = ByteArray(1024)
    var bytesRead: Int
    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
        byteArrayOutputStream.write(buffer, 0, bytesRead)
    }
    return byteArrayOutputStream.toByteArray()
}