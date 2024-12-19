package com.example.homeproject.data.datasources

import android.util.Log
import com.example.homeproject.data.datamodels.CurrentUser
import com.example.homeproject.data.datamodels.SendLogin
import com.example.homeproject.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentUserDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun registration(regData: SendLogin): CurrentUser {
        return regUser(regData)
    }

    suspend fun logIn(login: SendLogin): CurrentUser {
        return logInUser(login)
    }
    private suspend fun regUser(regData: SendLogin): CurrentUser {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.checkUserReg(regData)

                if (response.isSuccessful) {
                    val currentUser = response.body()
                    if (currentUser != null) {
                        currentUser
                    } else {
                        throw Exception("Response body is null")
                    }
                } else {
                    if (response.code() == 409) {
                        throw Exception("Пользователь уже существует")
                    } else {
                        throw Exception("Error: ${response.code()} - ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                Log.e("CheckUser", "Failed to register user", e)
                throw e
            }
        }
    }


    suspend fun logInUser(login: SendLogin): CurrentUser {
        return withContext(Dispatchers.IO){
            try{
                val response = apiService.checkUserLogIn(login)

                if (response.isSuccessful){
                    val currentUser = response.body()
                    if (currentUser != null) {
                        currentUser
                    } else {
                        throw Exception("Response body is null")
                    }
                }
                else {
                    if (response.code() == 401) {
                        throw Exception("Неверный пароль")
                    } else if ( response.code() == 404){
                        throw Exception("Пользователь не найден")
                    } else {
                        throw Exception("Ошибка подключения к серверу")
                    }
                }
            }
            catch (e: Exception){
                Log.e("logInUser", "Ошибка при входе: ${e.message}", e)
                throw e
            }
        }
    }
}