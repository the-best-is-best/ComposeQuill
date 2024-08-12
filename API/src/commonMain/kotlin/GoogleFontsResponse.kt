data class GoogleFonts(
    var id: Long = 0,
    val items: List<GoogleFontsItems>
)

internal data class GoogleFontsItems(
    val family: String,
)