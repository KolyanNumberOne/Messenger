package com.example.homeproject.data.retrofit

import com.example.homeproject.data.datamodels.CurrentUser
import com.example.homeproject.data.datamodels.Dialogues
import com.example.homeproject.data.datamodels.Friend
import com.example.homeproject.data.datamodels.Message
import com.example.homeproject.data.datamodels.SearchQuery
import com.example.homeproject.data.datamodels.SendLogin
import com.example.homeproject.data.datamodels.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("upload_image")
    suspend fun uploadImage(@Body encodedImage: String): Call<Void>

    @GET("get_image/{id}")
    suspend fun getImageById(@Path("id") id: Int): Call<String>

    @GET("getUsers")
    suspend fun getAllUsers(): Call<List<User>>

    @GET("getUserById")
    suspend fun getUserById(): Call<User>

    @POST("checkUserReg")
    suspend fun checkUserReg(@Body login: SendLogin): Response<CurrentUser>

    @POST("checkUserLogIn")
    suspend fun checkUserLogIn(@Body login: SendLogin): Response<CurrentUser>


    //Друзья
    @GET("getFriends")
    suspend fun getFriends(@Query("id") id: Int): Response<List<Friend>>

    //Поиск друзей
    @POST("searchUser")
    suspend fun searchUser(@Body query: SearchQuery): Response<List<Friend>>


    // Работа с диалогами
    @GET("getDialogues")
    suspend fun getDialogues(@Query("id") id: Int): Response<List<Dialogues>>


    //Сообщения
    @GET("getMessage")
    suspend fun getMessage(@Query("idDialog") idDialog: Int): Response<List<Message>>

    @PUT("updateMessage")
    suspend fun updateMessage(@Body message: Message): Response<Unit>

    @POST("sendMessage")
    suspend fun sendMessage(
        @Query("dialogId") dialogId: Int,
        @Body message: Message
    ): Response<Message>

    @DELETE("deleteMessage")
    suspend fun deleteMessage(@Body message: Message): Response<Unit>
}

