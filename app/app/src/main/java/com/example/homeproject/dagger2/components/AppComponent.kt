package com.example.homeproject.dagger2.components

import com.example.homeproject.dagger2.modules.ApiModule
import com.example.homeproject.dagger2.modules.AppModule
import com.example.homeproject.dagger2.modules.WebSocketModule
import com.example.homeproject.ui.fragments.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, WebSocketModule:: class] )
interface AppComponent {
    fun inject(fragment: MainFragment)
}