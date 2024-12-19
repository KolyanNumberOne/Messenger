package com.example.homeproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homeproject.ui.screens.login.LoginViewModel
import com.example.homeproject.R
import com.example.homeproject.dagger2.components.AuthComponent
import com.example.homeproject.dagger2.components.DaggerAuthComponent
import com.example.homeproject.dagger2.modules.AuthModule
import com.example.homeproject.ui.screens.login.RegistrationViewModel
import com.example.homeproject.ui.navigation.AuthNavigation
import javax.inject.Inject

class AuthFragment: Fragment() {
    private lateinit var authComponent: AuthComponent

    @Inject lateinit var registrationViewModel: RegistrationViewModel
    @Inject lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                AuthNavigation(loginViewModel, registrationViewModel, onMainPageClick = {
                    findNavController().navigate(R.id.action_authFragment_to_mainFragment)
                })
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        authComponent = DaggerAuthComponent.builder()
            .authModule(AuthModule())
            .build()

        authComponent.inject(this)

    }
}
