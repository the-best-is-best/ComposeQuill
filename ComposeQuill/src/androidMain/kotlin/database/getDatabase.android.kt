package database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

class AndroidDatabase {
    companion object {
        private lateinit var appContext: Context
        fun init(context: Context) {
            appContext = context
        }

        internal fun getContext(): Context = appContext
    }
}

internal actual fun getGoogleFontsDatabase(): GoogleFontsDatabase {
    val dbFile = AndroidDatabase.getContext().getDatabasePath("google_fonts.db")
    return Room.databaseBuilder<GoogleFontsDatabase>(
        context = AndroidDatabase.getContext(),
        name = dbFile.absolutePath
    ).setDriver(BundledSQLiteDriver())
        .build()

}