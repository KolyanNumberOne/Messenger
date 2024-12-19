package com.example.homeproject.ui.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeproject.data.datamodels.SendLogin
import com.example.homeproject.data.datamodels.token
import com.example.homeproject.data.repositories.CurrentUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginViewModel @Inject constructor (
    private val currentUserRepository: CurrentUserRepository
): ViewModel() {

    //private val repository: UserRepository

    private val _login = MutableLiveData<String>()
    val login: LiveData<String> = _login

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private var errorJob: Job? = null
    //init {
    //val userDb = UserRoomDatabase.getInstance(application)
    //val userDao = userDb.userDao()
    //repository = UserRepository(userDao)
    //}

    fun updateLogin(newLogin: String) {
        _login.value = newLogin
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun checkUser(): Boolean{
        return if (checkFields()){
            var flag: Boolean
            runBlocking { flag = checkUserLogIn() }
            flag
        }
        else {
            false
        }
    }

    private suspend fun checkUserLogIn(): Boolean{
        return try {
            val result = withContext(Dispatchers.IO) {
                currentUserRepository.logIn(SendLogin(login.value?: "", password.value?: ""))
            }
            token.setCurrentUser(result)
            true
        } catch (e: Exception) {
            setErrorMessage(e.message ?: "Unknown error")
            false
        }
    }

    fun checkFields(): Boolean{
        return if (login.value.isNullOrEmpty() && password.value.isNullOrEmpty()) {
            setErrorMessage("Заполните поля")
            false
        } else if (login.value.isNullOrEmpty()) {
            setErrorMessage("Заполните логин")
            false
        } else if (password.value.isNullOrEmpty()) {
            setErrorMessage("Заполните пароль")
            false
        } else {
            true
        }
    }



    private fun setErrorMessage(message: String) {
        errorJob?.cancel()

        _errorMessage.value = message

        errorJob = viewModelScope.launch {
            delay(3000)
            _errorMessage.value = ""
        }
    }
}