package models

import enum.QuillType

import kotlinx.serialization.Serializable

@Serializable
internal data class QuillParser(
    val type: QuillType,
    val value: String? = null,

    )


