package ru.mymess.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mymess.DataModels.Dialogues
import ru.mymess.DataModels.Message
import ru.mymess.DataModels.sendDialogues
import ru.mymess.database.ConnectDB
import ru.mymess.database.User

fun Application.getMessage() {
    routing {
        get("/getMessage") {
            val idDialog = call.request.queryParameters["idDialog"]?.toIntOrNull()




            if (idDialog == null) {
                call.respond(HttpStatusCode.BadRequest, "Token is missing")
                return@get
            }

            val db = ConnectDB()
            val result = db.getMessagesByDialogId(idDialog)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}