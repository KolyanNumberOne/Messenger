package com.example.homeproject.data.datasources

import android.util.Log
import com.example.homeproject.data.datamodels.Friend
import com.example.homeproject.data.datamodels.SearchQuery
import com.example.homeproject.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersDataSources @Inject constructor(private val apiService: ApiService){
    suspend fun getFriends(id: Int): List<Friend> {
        return getFriendsFromServer(id)
    }

    private suspend fun getFriendsFromServer(id: Int): List<Friend>{
        val response =  withContext(Dispatchers.IO){
            apiService.getFriends(id)
        }
        return if (response.isSuccessful){
            response.body()?: emptyList()
        }
        else {
            Log.e("Friends", "Failed to get Friends. Error code: ${response.code()}")
            throw Exception("Failed to get Friends. Error code: ${response.code()}")
        }
    }

    suspend fun searchUsers(query: String): List<Friend> {
        val response = withContext(Dispatchers.IO){
            apiService.searchUser(SearchQuery(query))
        }
        return if (response.isSuccessful){
            response.body()?: emptyList()
        }else{
            throw Exception("Ничего не найдено")
        }
    }
}