package com.example.homeproject.data.datasources

import android.util.Log
import com.example.homeproject.data.datamodels.Dialogues
import com.example.homeproject.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DialoguesDataSource @Inject constructor(private val apiService: ApiService){
    suspend fun getDialogues(token: Int): List<Dialogues> {
        return fetchDialogues(token)
    }

    private suspend fun fetchDialogues(id: Int): List<Dialogues> {
        val response = withContext(Dispatchers.IO){
            apiService.getDialogues(id)
        }
        return if (response.isSuccessful){
            response.body()?: emptyList()
        }
        else {
            Log.e("CheckDialogues", "Failed to get Dialogues. Error code: ${response.code()}")
            throw Exception("Failed to get Dialogues. Error code: ${response.code()}")
        }
    }
}