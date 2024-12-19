package ru.mymess.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mymess.database.ListUsers

fun Application.getUsers() {
    routing {
        get("/getUsers") {
            val users = ListUsers.getAllUsers()
            call.respond(users)
        }
    }
}
