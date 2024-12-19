package ru.mymess.database

import kotlinx.serialization.Serializable

@Serializable
data class SearchQuery(
    val query: String
)