package utils

import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.cinterop.BetaInteropApi
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.create
import platform.Foundation.writeToURL

internal actual class Base64Converter {
    @OptIn(BetaInteropApi::class)
    actual fun convertBase64ToImage(
        base64String: String,
        callback: (PlatformFile) -> Unit
    ) {
        val imageFilePath = "${getCachePath()}/image_quill.png"
        val fileURL = NSURL.fileURLWithPath(imageFilePath)
        val nsData = NSData.create(base64EncodedString = base64String, options = 0U)
        nsData!!.writeToURL(fileURL, true)
        // Convert the file to a Uri (NSURL)
        callback(PlatformFile(fileURL))
    }


}