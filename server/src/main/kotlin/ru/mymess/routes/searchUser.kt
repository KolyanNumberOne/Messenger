package ru.mymess.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mymess.database.ConnectDB
import ru.mymess.database.SearchQuery
import ru.mymess.features.login.ReceiveLogin

fun Application.searchUser() {
    routing {
        post("/searchUser") {
            val searchQuery = call.receive(SearchQuery::class)
            val db = ConnectDB()

            val results = db.searchUsersByName(searchQuery.query)

            if (results.isNotEmpty()) {
                call.respond(HttpStatusCode.OK, results)
            } else {
                call.respond(HttpStatusCode.NotFound, "Users not found")
            }
        }
    }
}