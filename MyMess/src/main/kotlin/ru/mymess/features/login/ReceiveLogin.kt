package ru.mymess.features.login

import kotlinx.serialization.Serializable

@Serializable
data class ReceiveLogin(
    val login:String,
    val password:String
)
