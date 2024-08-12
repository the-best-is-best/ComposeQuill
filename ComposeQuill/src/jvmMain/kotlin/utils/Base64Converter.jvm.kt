package utils

import io.github.vinceglb.filekit.core.PlatformFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Base64

internal actual class Base64Converter {
    actual fun convertBase64ToImage(
        base64String: String,
        callback: (PlatformFile) -> Unit
    ) {
        val decodedBytes = Base64.getDecoder().decode(base64String)
        val imageFile = File(getCachePath(), "image_quill.png")
        try {
            FileOutputStream(imageFile).use { outputStream ->
                outputStream.write(decodedBytes)
            }
            callback(PlatformFile(imageFile))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}