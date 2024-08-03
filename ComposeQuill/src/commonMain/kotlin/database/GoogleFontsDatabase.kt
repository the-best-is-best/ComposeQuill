package database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GoogleFonts::class],
    version = 1
)
abstract class GoogleFontsDatabase : RoomDatabase() {
    internal abstract fun googleFontsDao(): GoogleFontsDao
}