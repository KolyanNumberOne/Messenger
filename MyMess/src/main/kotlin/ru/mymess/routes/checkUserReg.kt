package ru.mymess.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.launch
import ru.mymess.DataModels.CurrentUser
import ru.mymess.database.ConnectDB
import ru.mymess.features.login.ReceiveLogin

fun Application.checkUserReg() {
    routing {
        post("/checkUserReg") {
            // Получаем данные для регистрации
            val reg = call.receive(ReceiveLogin::class)
            val db = ConnectDB()

            // Проверяем, есть ли такой пользователь в базе данных
            val userFound = db.isUserExist(login = reg.login)

            if (userFound) {
                // Возвращаем ошибку 409 (Conflict), если пользователь уже существует
                call.respond(HttpStatusCode.Conflict, "User already registered")
            } else {
                // Если пользователя нет, регистрируем его
                db.insertUser(login = reg.login, password = reg.password)
                val currentUser = db.getUserByLogin(login =  reg.login)!!

                // Возвращаем успешный ответ с текущими данными пользователя
                call.respond(HttpStatusCode.OK, CurrentUser(
                    id = currentUser.id,
                    login = currentUser.login,
                    userName = currentUser.userName,
                    password = currentUser.password,
                    image = ""
                ))
            }
        }
    }
}
