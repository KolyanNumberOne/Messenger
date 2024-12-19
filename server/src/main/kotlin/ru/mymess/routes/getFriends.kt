package ru.mymess.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mymess.DataModels.Dialogues
import ru.mymess.DataModels.Friend
import ru.mymess.DataModels.SendFriend
import ru.mymess.DataModels.sendDialogues
import ru.mymess.database.ConnectDB
import ru.mymess.database.User

fun Application.getFriends() {
    routing {
        get("/getFriends") {
            val id = call.request.queryParameters["id"]?.toIntOrNull()

            val result = mutableListOf<SendFriend>()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Token is missing")
                return@get
            }

            val db = ConnectDB()
            val friends: List<Friend> = db.getFriends(id) // Функция для получения диалогов
            friends.forEach {
                val friend: User
                if (it.idUser == id){
                    friend = db.getUserById(it.idFriend)
                }
                else{
                    friend = db.getUserById(it.idUser)
                }
                result.add(SendFriend(it.id, friend.id, friend.userName, friend.image?:""))
            }
            call.respond(HttpStatusCode.OK, result)
        }
    }
}