package database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.nio.file.Paths

actual fun getGoogleFontsDatabase(): GoogleFontsDatabase {
    val dbPath =
        Paths.get(System.getProperty("user.dir"), "google_fonts.db").toAbsolutePath().toString()


    return Room.databaseBuilder<GoogleFontsDatabase>(
        name = dbPath,
    ).setDriver(BundledSQLiteDriver())
        .build()

}