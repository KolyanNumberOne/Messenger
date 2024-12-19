package ru.mymess.DataModels

import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    val id: Int,
    val idUser: Int,
    val idFriend: Int
)

@Serializable
data class SendFriend(
    val id: Int,
    val idFriend: Int,
    val userName: String,
    val image: String
)