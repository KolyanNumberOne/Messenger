package ru.mymess.database

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Int, val login: String, val userName: String, val password: String, val image: String?)

