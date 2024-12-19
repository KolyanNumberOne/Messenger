package ru.mymess.plugins

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: Int,
    val blob: ByteArray
)
