package utils

import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual fun getCachePath(): String {
    val paths = NSSearchPathForDirectoriesInDomains(
        NSCachesDirectory, // Specifies the cache directory
        NSUserDomainMask,                      // Specifies the user's home directory
        true                                   // Expands the tilde (~) to a full path
    )
    return (paths.firstOrNull() as? String) ?: ""
}