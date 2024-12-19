package com.example.homeproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homeproject.ui.screens.login.LoginPage
import com.example.homeproject.ui.screens.login.LoginViewModel
import com.example.homeproject.ui.screens.login.RegistrationPage
import com.example.homeproject.ui.screens.login.RegistrationViewModel

@Composable
fun AuthNavigation(
    loginViewModel: LoginViewModel,
    registrationViewModel: RegistrationViewModel,
    onMainPageClick: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "registration") {
        composable("registration") {
            RegistrationPage.Registration(registrationViewModel, navController, onMainPageClick)
        }
        composable("loginPage") {
            LoginPage.Login(loginViewModel, navController, onMainPageClick)
        }
    }
}