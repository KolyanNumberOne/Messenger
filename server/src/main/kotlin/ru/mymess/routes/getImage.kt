package ru.mymess.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mymess.database.ConnectDB

fun Application.getImage() {
    routing {
        get("/get_image/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val db = ConnectDB()
            var result = db.getImageById(id!!)
            call.respond(result!!)
        }
    }
}