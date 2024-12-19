package com.example.homeproject.data.datamodels

import kotlinx.serialization.Serializable

@Serializable
data class Message(val idDialog: Int,val id_sender: Int, val message: String, val timeMessage: String)
