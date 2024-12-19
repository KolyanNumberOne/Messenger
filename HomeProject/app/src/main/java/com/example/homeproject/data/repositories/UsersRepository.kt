package com.example.homeproject.data.repositories


import com.example.homeproject.data.datamodels.Friend
import com.example.homeproject.data.datasources.UsersDataSources
import javax.inject.Inject

interface UsersRepository{
    suspend fun getFriends(id: Int): List<Friend>

    suspend fun searchUsers(query: String): List<Friend>
}
class UsersRepositoryImp @Inject constructor(
    private val dataSource: UsersDataSources
): UsersRepository {
    override suspend fun getFriends(id: Int): List<Friend> = dataSource.getFriends(id)

    override suspend fun searchUsers(query: String): List<Friend>  = dataSource.searchUsers(query)
}
