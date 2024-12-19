package com.example.homeproject.data.repositories

import com.example.homeproject.data.datamodels.CurrentUser
import com.example.homeproject.data.datasources.CurrentUserDataSource
import com.example.homeproject.data.datamodels.SendLogin
import javax.inject.Inject

interface CurrentUserRepository{
    suspend fun registration(regData: SendLogin): CurrentUser
    suspend fun logIn(login: SendLogin): CurrentUser
}
class CurrentUserRepositoryImp @Inject constructor(
    private val dataSource: CurrentUserDataSource
): CurrentUserRepository {
    override suspend fun registration(regData: SendLogin): CurrentUser = dataSource.registration(regData)

    override suspend fun logIn(login: SendLogin): CurrentUser = dataSource.logIn(login)
}

