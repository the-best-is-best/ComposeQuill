package io.tbib.composequill.services

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileInputStream

internal fun convertUriImageToFile(uri: Uri, context: Context): String? {
    // Handle the URI here
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            return try {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                it.getString(columnIndex)
                val file = File(it.getString(columnIndex))
                val fileInputStream = FileInputStream(file)
                val bytes = ByteArray(file.length().toInt())
                fileInputStream.read(bytes)
                fileInputStream.close()
                file.path
            } catch (e: Exception) {
                null
            }
        }

    }
    return null

}
