package com.example.homeproject.data.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.homeproject.data.datamodels.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getUsers(): LiveData<List<User>>

    @Insert
    fun addUser(user: User)

    @Query("DELETE FROM users WHERE userId = :id")
    fun deleteUser(id:Int)
}