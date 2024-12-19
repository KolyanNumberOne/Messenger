package ru.mymess.routes

import com.sun.tools.javac.util.Log
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.launch
import ru.mymess.DataModels.CurrentUser
import ru.mymess.database.ConnectDB
import ru.mymess.features.login.ReceiveLogin

fun Application.checkUserLogIn() {
    routing {
        post("/checkUserLogIn") {
            // Получаем данные для регистрации
            val login = call.receive(ReceiveLogin::class)
            val db = ConnectDB()

            // Проверяем, есть ли такой пользователь в базе данных
            val userFound = db.getUserByLogin(login = login.login)

            if (userFound != null) {

                if (userFound.password == login.password){
                    call.respond(
                        HttpStatusCode.OK,
                        CurrentUser(
                            id = userFound.id,
                            login = userFound.login,
                            userName = userFound.userName,
                            password = userFound.password,
                            image = "")
                    )
                } else{
                    call.respond(HttpStatusCode.Unauthorized, "Invalid password")
                }
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found")
            }
            println("userFound is of type: ${userFound?.let { it::class.simpleName } ?: "null"}")
            println("User found: $userFound")
        }
    }
}