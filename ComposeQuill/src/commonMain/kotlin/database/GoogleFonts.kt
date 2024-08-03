package database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class GoogleFonts(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val items: List<GoogleFontsItems>
)

@Entity
internal data class GoogleFontsItems(
    val family: String,
)