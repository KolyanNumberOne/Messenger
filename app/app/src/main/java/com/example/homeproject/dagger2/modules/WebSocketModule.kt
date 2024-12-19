package com.example.homeproject.dagger2.modules

import com.example.homeproject.data.websocket.WebSocketClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WebSocketModule(){
    @Singleton
    @Provides
    fun provideWebSocketClient(): WebSocketClient {
        return WebSocketClient()
    }
}