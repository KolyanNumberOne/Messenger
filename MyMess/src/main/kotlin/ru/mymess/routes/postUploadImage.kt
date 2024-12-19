package ru.mymess.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mymess.database.ConnectDB




fun Application.postUploadImage() {
    routing {
        post("/upload_image") {
            val imageBytes = call.receive<ByteArray>()
            val db = ConnectDB()
            db.uploadImage(imageBytes)
            call.respondText("Image uploaded successfully")
        }
    }
}


