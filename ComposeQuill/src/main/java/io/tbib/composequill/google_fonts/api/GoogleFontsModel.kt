package io.tbib.composequill.google_fonts.api

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

internal data class GoogleFontsModel(
    @Id var id: Long = 0,
    val items: List<GoogleFontsItemsModel>

)

@Entity
internal data class GoogleFontsItemsModel(
    @Id var id: Long = 0,
    val family: String,
//    val variants:List<String>,
//    val subsets:List<String>,
//    val version:String,
//    val lastModified:String,
//    val files:GoogleFontsFilesModel
)
