package ru.mymess.database

object ListUsers {
    var listUsers: MutableList<User> = mutableListOf()

    fun addUser(user: User) {
        listUsers.add(user)
    }

    fun getAllUsers(): List<User> {
        return listUsers.toList()
    }

    fun findUserByName(userName: String): User? {
        return listUsers.find { it.userName == userName }
    }
}