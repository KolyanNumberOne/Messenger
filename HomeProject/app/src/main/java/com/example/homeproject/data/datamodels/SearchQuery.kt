package com.example.homeproject.data.datamodels

import kotlinx.serialization.Serializable

@Serializable
data class SearchQuery(
    val query: String
)
