package com.example.homeproject.dagger2.modules

import android.app.Application
import com.example.homeproject.data.datasources.DialoguesDataSource
import com.example.homeproject.data.datasources.MessageDataSource
import com.example.homeproject.data.datasources.UsersDataSources
import com.example.homeproject.data.repositories.DialoguesRepository
import com.example.homeproject.data.repositories.DialoguesRepositoryImp
import com.example.homeproject.data.repositories.MessageRepository
import com.example.homeproject.data.repositories.MessageRepositoryImp
import com.example.homeproject.data.repositories.UsersRepository
import com.example.homeproject.data.repositories.UsersRepositoryImp
import com.example.homeproject.data.retrofit.ApiService
import com.example.homeproject.data.websocket.WebSocketClient
import com.example.homeproject.ui.screens.chat.MessageViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application


    //Друзья
    @Provides
    @Singleton
    fun provideUsersRepository(dataSource: UsersDataSources): UsersRepository = UsersRepositoryImp(dataSource)

    @Provides
    @Singleton
    fun provideUsersDataSource(apiService: ApiService): UsersDataSources = UsersDataSources(apiService)

    //Диалоги
    @Provides
    @Singleton
    fun provideDialoguesRepository(dataSource: DialoguesDataSource): DialoguesRepository = DialoguesRepositoryImp(dataSource)

    @Provides
    @Singleton
    fun provideDialoguesDataSource(apiService: ApiService): DialoguesDataSource = DialoguesDataSource(apiService)


    //Сообщения
    @Provides
    @Singleton
    fun provideMessageRepository(messageDataSource: MessageDataSource): MessageRepository = MessageRepositoryImp(messageDataSource)

    @Provides
    @Singleton
    fun provideMessageDataSource(apiService: ApiService): MessageDataSource = MessageDataSource(apiService)

    @Provides
    fun provideMessageViewModelFactory(
        messageRepository: MessageRepository,
        webSocketClient: WebSocketClient
    ): MessageViewModelFactory {
        return MessageViewModelFactory(messageRepository, webSocketClient)
    }

}