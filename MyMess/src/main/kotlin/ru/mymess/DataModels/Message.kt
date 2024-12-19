package ru.mymess.DataModels

import kotlinx.serialization.Serializable

@Serializable
data class Message(val id: Int, val idDialog: Int,val id_sender: Int, val message: String, val timeMessage: String)

@Serializable
data class ReceiveMessage(val idDialog: Int,val id_sender: Int, val message: String, val timeMessage: String)