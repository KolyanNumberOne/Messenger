package ru.mymess.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mymess.database.ListUsers
import ru.mymess.features.login.ReceiveLogin
import ru.mymess.generateRandomToken

fun Application.postLogin() {
    routing {
        post("/login") {
            val login = call.receive(ReceiveLogin::class)
            val bdLogin = ListUsers.listUsers

            val userFound = bdLogin.any { it.login == login.login && it.password == login.password }

            if (userFound) {
                call.respond(generateRandomToken(32))
            } else {
                call.respond("Пользователь с указанным именем пользователя и паролем не найден")
            }
        }
    }
}
