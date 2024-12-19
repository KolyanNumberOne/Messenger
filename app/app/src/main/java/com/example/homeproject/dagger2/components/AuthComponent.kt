package com.example.homeproject.dagger2.components

import com.example.homeproject.dagger2.modules.ApiModule
import com.example.homeproject.dagger2.modules.AuthModule
import com.example.homeproject.ui.fragments.AuthFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AuthModule::class, ApiModule::class] )
interface AuthComponent {
    fun inject(fragment: AuthFragment)
}