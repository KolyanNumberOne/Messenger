package com.example.homeproject.dagger2.modules

import com.example.homeproject.data.datasources.CurrentUserDataSource
import com.example.homeproject.data.repositories.CurrentUserRepository
import com.example.homeproject.data.repositories.CurrentUserRepositoryImp
import com.example.homeproject.data.retrofit.ApiService
import com.example.homeproject.ui.screens.login.LoginViewModel
import com.example.homeproject.ui.screens.login.RegistrationViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthModule(){

    @Provides
    @Singleton
    fun provideCurrentUserDataSource(apiService: ApiService): CurrentUserDataSource {
        return CurrentUserDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideCurrentUserRepository(dataSource: CurrentUserDataSource): CurrentUserRepository {
        return CurrentUserRepositoryImp(dataSource)
    }

    @Provides
    @Singleton
    fun provideRegistrationViewModel(currentUserRepository: CurrentUserRepository): RegistrationViewModel {
        return RegistrationViewModel(currentUserRepository)
    }

    @Provides
    @Singleton
    fun provideLoginViewModel(currentUserRepository: CurrentUserRepository): LoginViewModel {
        return LoginViewModel(currentUserRepository)
    }
}