package com.tbib.ttextricheditor.models

import com.tbib.ttextricheditor.enum.TTextRichType
import kotlinx.serialization.Serializable

@Serializable
internal data class TTextRichParser(
    val type: TTextRichType,
    val value: String,

    )
