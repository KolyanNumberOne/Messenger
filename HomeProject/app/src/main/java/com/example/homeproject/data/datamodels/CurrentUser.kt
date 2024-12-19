package com.example.homeproject.data.datamodels
import kotlinx.serialization.Serializable

@Serializable
data class CurrentUser (
    var id: Int = 0,
    var login: String = "",
    var userName: String="",
    var password: String="",
    var image: String=""
)

object token{
    var id: Int = 0
    var login: String = ""
    var userName: String=""
    var password: String=""
    var image: String=""

    fun setCurrentUser(currentUser: CurrentUser) {
        id = currentUser.id
        login = currentUser.login
        userName = currentUser.userName
        password = currentUser.password
        image = currentUser.image
    }

    fun clearToken(){
        id = 0
        login = ""
        userName = ""
        password = ""
        image = ""
    }

    fun displayToken(): String {
        return "ID: $id, Login: $login, UserName: $userName, Password: $password, Image: $image"
    }
}