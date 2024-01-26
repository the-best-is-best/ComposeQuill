package io.tbib.composequill.models

import io.tbib.composequill.enum.QuillType
import kotlinx.serialization.Serializable

@Serializable
internal data class QuillParser(
    val type: QuillType,
    val value: String? = null,

    )


