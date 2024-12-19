package com.example.homeproject.data.datamodels

sealed class ResultReg<out T> {
    data class Success<out T>(val data: T) : ResultReg<T>()
    data class Error(val exception: Exception, val errorMessage: String? = null) : ResultReg<Nothing>()
}