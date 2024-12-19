package com.example.homeproject.ui.screens.login

import android.util.Log
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

class RegistrationViewModel @Inject constructor (
    private val currentUserRepository: CurrentUserRepository
): ViewModel() {

    //private val repository: UserRepository

    private val _login = MutableLiveData<String>()
    val login: LiveData<String> = _login

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

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

    fun updateConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun checkPassword(): Boolean {
        return if (password.value == confirmPassword.value) {
            true
        } else {
            setErrorMessage("Пароли не совпадают")
            false
        }
    }

    fun checkUserToReg(): Boolean {
        return if (checkFields() && checkPassword()) {
            var isSuccessful = false
            runBlocking {
                isSuccessful = checkUser()
            }
            isSuccessful
        } else {
            false
        }
    }


    private suspend fun checkUser(): Boolean {
        return try {
            val result = withContext(Dispatchers.IO) {
                currentUserRepository.registration(
                    SendLogin(
                        login.value ?: "",
                        password.value ?: ""
                    )
                )
            }
            token.setCurrentUser(result)
            true
        } catch (e: Exception) {
            Log.e("CheckUser", "Exception: ${e.message}")
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
        } else if (confirmPassword.value.isNullOrEmpty()) {
            setErrorMessage("Заполните подтверждение пароля")
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