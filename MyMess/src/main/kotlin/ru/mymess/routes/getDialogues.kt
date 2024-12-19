package ru.mymess.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mymess.DataModels.Dialogues
import ru.mymess.DataModels.sendDialogues
import ru.mymess.database.ConnectDB
import ru.mymess.database.User

fun Application.getDialogues() {
    routing {
        get("/getDialogues") {
            val id = call.request.queryParameters["id"]?.toIntOrNull()


            val result = mutableListOf<sendDialogues>()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Token is missing")
                return@get
            }

            val db = ConnectDB()
            val dialogues: List<Dialogues> = db.getAllDialogues(id) // Функция для получения диалогов
            dialogues.forEach {
                val friend: User
                if (it.participant1 == id){
                    friend = db.getUserById(it.participant2)
                }
                else{
                    friend = db.getUserById(it.participant1)
                }
                result.add(sendDialogues(id = it.id, nameFriend = friend.userName, lastMessage = "Тест", image = friend.image))
            }
            call.respond(HttpStatusCode.OK, result)
        }
    }
}
