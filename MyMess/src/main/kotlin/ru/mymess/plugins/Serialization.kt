package ru.mymess.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*  // Импортируйте модуль JSON

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()  // Используйте json() для настройки JSON-сериализации
    }
}
