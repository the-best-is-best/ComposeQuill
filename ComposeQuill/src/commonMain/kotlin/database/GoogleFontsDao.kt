package database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface GoogleFontsDao {
    @Upsert
    suspend fun upsert(data: GoogleFonts)

    @Query("SELECT * FROM GoogleFonts")
    fun getAll(): List<GoogleFonts>


}