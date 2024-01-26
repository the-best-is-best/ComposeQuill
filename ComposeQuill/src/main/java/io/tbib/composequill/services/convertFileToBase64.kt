package io.tbib.composequill.services

import android.util.Base64
import java.io.File

internal fun convertFileToBase64(path: String): String {
    val file = File(path)
    val bytes = file.readBytes()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}