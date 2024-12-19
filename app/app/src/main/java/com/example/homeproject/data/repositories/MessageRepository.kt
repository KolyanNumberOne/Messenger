package com.example.homeproject.data.repositories

import com.example.homeproject.data.datamodels.Message
import com.example.homeproject.data.datasources.MessageDataSource
import javax.inject.Inject

interface MessageRepository{
    suspend fun getMessage(idDialog:Int): List<Message>

    suspend fun updateMessage(message: Message)

    suspend fun deleteMessage(message: Message)

}
class MessageRepositoryImp @Inject constructor(private val dataSource: MessageDataSource):
    MessageRepository {

    override suspend fun getMessage(idDialog: Int):  List<Message> = dataSource.getMessage(idDialog)

    override suspend fun updateMessage(message: Message) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessage(message: Message) {
        TODO("Not yet implemented")
    }
}