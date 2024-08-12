package utils

import io.github.vinceglb.filekit.core.PlatformFile

internal expect class Base64Converter() {
    fun convertBase64ToImage(base64String: String, callback: (PlatformFile) -> Unit)
}