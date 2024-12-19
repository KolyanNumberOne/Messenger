package com.example.homeproject.ui.screens.friends

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeproject.data.datamodels.Friend
import com.example.homeproject.data.datamodels.token
import com.example.homeproject.data.repositories.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    private val usersRepository: UsersRepository
): ViewModel() {

    private val _listFriend = MutableStateFlow<List<Friend>>(emptyList())
    val listFriend: StateFlow<List<Friend>> = _listFriend

            private val _searchResults = MutableStateFlow<List<Friend>>(emptyList())
            val searchResults: StateFlow<List<Friend>> = _searchResults


            init {
                viewModelScope.launch {
                    _listFriend.value = usersRepository.getFriends(token.id)
                }
    }

    fun searchUser(query: String) {
        viewModelScope.launch {
            try {
                if (query.isBlank()) {
                    _searchResults.value = emptyList()
                } else {
                    val results = usersRepository.searchUsers(query)
                    _searchResults.value = results
                }
            } catch (e: Exception) {
                Log.e("FriendsViewModel", "Ошибка при поиске пользователя: ${e.message}")
                _searchResults.value = emptyList()
            }
        }
    }
}