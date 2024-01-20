package com.tbib.composequill.models

import com.tbib.composequill.enum.QuillType
import kotlinx.serialization.Serializable

@Serializable
internal data class QuillParser(
    val type: QuillType,
    val value: String? = null,

    )


