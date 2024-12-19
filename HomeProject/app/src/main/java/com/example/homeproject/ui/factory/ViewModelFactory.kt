package com.example.homeproject.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ViewModelFactory<T : ViewModel>(
    private val application: Application,
    private val viewModelClass: Class<T>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModelClass)) {
            return viewModelClass.getConstructor(Application::class.java).newInstance(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

