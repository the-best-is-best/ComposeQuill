package utils

import ComposeQuillAndroid
import android.net.Uri
import io.github.vinceglb.filekit.core.PlatformFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

internal actual class Base64Converter {
    actual fun convertBase64ToImage(
        base64String: String,
        callback: (PlatformFile) -> Unit
    ) {
        val decodedBytes = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
        val imageFile = File(getCachePath(), "image_quill.png")
        try {
            FileOutputStream(imageFile).use { outputStream ->
                outputStream.write(decodedBytes)
            }
            callback(
                PlatformFile(
                    Uri.parse(imageFile.absolutePath),
                    ComposeQuillAndroid.appContext
                )
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}