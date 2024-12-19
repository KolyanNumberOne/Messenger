package ru.mymess.database

object ListOnlineUsers {

    var listOnlineUsers: MutableMap<String, User> = mutableMapOf()

    fun addOnlineUser(token: String, user: User) {
        listOnlineUsers.put(token, user)
    }

    fun delOnlineUser(token:String){
        listOnlineUsers.remove(token)
    }
}