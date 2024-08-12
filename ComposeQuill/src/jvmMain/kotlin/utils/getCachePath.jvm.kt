package utils

actual fun getCachePath(): String {
    return System.getProperty("java.io.tmpdir")
}