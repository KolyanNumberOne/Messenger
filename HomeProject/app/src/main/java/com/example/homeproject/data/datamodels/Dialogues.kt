package com.example.homeproject.data.datamodels

import kotlinx.serialization.Serializable

@Serializable
data class Dialogues(
    val id: Int,
    val image: String,
    val nameFriend : String,
    val lastMessage: String
)
