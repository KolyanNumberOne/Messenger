package com.example.homeproject.data.datamodels

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "userId")
    var id: Int = 0,
    @ColumnInfo(name = "login")
    var login: String="",
    @ColumnInfo(name = "userName")
    var userName: String="",
    @ColumnInfo(name = "password")
    var password: String="",
    @ColumnInfo(name = "userImage")
    var image: String=""
)