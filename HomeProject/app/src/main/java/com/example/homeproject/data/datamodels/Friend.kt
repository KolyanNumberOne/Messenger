package com.example.homeproject.data.datamodels

import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    val id: Int,
    val idFriend: Int,
    val userName: String,
    val image: String
)