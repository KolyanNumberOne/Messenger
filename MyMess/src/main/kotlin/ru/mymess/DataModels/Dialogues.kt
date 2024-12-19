package ru.mymess.DataModels

import kotlinx.serialization.Serializable
import ru.mymess.plugins.Image

@Serializable
data class Dialogues(
    val id: Int,
    val participant1:Int,
    val participant2:Int
)

@Serializable
data class sendDialogues(
    val id: Int,
    val nameFriend: String,
    val lastMessage: String,
    val image: String?
)