package com.example.homeproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homeproject.ui.screens.chat.ChatPage
import com.example.homeproject.ui.screens.chat.ChatsViewModel
import com.example.homeproject.ui.screens.chat.MessageViewModelFactory
import com.example.homeproject.ui.screens.friends.Friends
import com.example.homeproject.ui.screens.friends.FriendsViewModel
import com.example.homeproject.ui.screens.home.HomePage
import com.example.homeproject.ui.screens.profile.Profile

@Composable
fun MainNavigation(
    chatsViewModel: ChatsViewModel,
    messageViewModelFactory: MessageViewModelFactory,
    friendsViewModel: FriendsViewModel,
    onAuthPageClick: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainPage") {
        composable("mainPage") { HomePage.HomeMenu(navController, chatsViewModel) }
        composable("profile") { Profile.profile(chatsViewModel, navController) }
        composable("friends") { Friends.Friends(friendsViewModel, navController) }
        composable("chat/{dialogId}") { backStackEntry ->
            val dialogId = backStackEntry.arguments?.getString("dialogId")?.toIntOrNull()
            if (dialogId != null) {
                val messageViewModel = remember(dialogId) {
                    messageViewModelFactory.create(dialogId)
                }
                ChatPage.Chat(messageViewModel, navController)
            }
        }
    }
}