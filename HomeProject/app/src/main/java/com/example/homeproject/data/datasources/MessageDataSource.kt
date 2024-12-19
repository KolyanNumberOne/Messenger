package com.example.homeproject.data.datasources

import android.util.Log
import com.example.homeproject.data.datamodels.Message
import com.example.homeproject.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessageDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getMessage(idDialog: Int):  List<Message>{
       return getMessageFromServer(idDialog)
    }

    suspend fun getMessageFromServer(idDialog: Int):  List<Message>{
        val response = withContext(Dispatchers.IO){
            apiService.getMessage(idDialog)
        }
        return if (response.isSuccessful){
             response.body()?: emptyList()
        }
        else {
            Log.e("CheckMessage", "Failed to get Messages. Error code: ${response.code()}")
            throw Exception("Failed to get Messages. Error code: ${response.code()}")
        }

    }

    suspend fun updateMessage(message: Message){
        withContext(Dispatchers.IO){
            //val response = apiService.sendMessage()
        }
    }

    suspend fun deleteMessage(message: Message){
        withContext(Dispatchers.IO){
            val response = apiService.deleteMessage(message)
        }
    }
}