package ru.mymess

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.mymess.database.ConnectDB
import ru.mymess.plugins.*
import ru.mymess.routes.*
import java.time.Duration

fun main() {
    val db = ConnectDB()
    runBlocking {
        launch {
            db.loadUsersIntoList()
            println("Пользователи успешно загружены при запуске сервера")
        }
    }
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    postLogin()
    getUsers()
    postUploadImage()
    getImage()


    checkUserReg()
    checkUserLogIn()


    getDialogues()
    getMessage()

    getFriends()
    searchUser()

    install(WebSockets) {
        pingPeriod = Duration.ofMinutes(1)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    chatWebSocket()
}


