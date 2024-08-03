package database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory


internal actual fun getGoogleFontsDatabase(): GoogleFontsDatabase {
    val dbFile = NSHomeDirectory() + "/google_fonts.db"
    return Room.databaseBuilder<GoogleFontsDatabase>(
        name = dbFile,
        factory = { GoogleFontsDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
        .build()

}