package utils

import ComposeQuillAndroid

actual fun getCachePath(): String {
    val context = ComposeQuillAndroid.appContext
    return context.cacheDir.absolutePath
}