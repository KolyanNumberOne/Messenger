package com.example.homeproject.data.datamodels

import kotlinx.serialization.Serializable

@Serializable
data class SendLogin(
    val login:String,
    val password:String
)