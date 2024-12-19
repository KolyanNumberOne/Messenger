package ru.mymess.DataModels

import kotlinx.serialization.Serializable

@Serializable
data class CurrentUser (
    var id: Int = 0,
    var login: String = "",
    var userName: String="",
    var password: String="",
    var image: String=""
)