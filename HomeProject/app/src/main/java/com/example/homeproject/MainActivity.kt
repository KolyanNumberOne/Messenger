package com.example.homeproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homeproject.dagger2.components.AppComponent
import com.example.homeproject.ui.screens.home.HomePage
import com.example.homeproject.ui.screens.chat.ChatPage
import com.example.homeproject.ui.screens.chat.ChatsViewModel
import com.example.homeproject.ui.screens.chat.MessageViewModelFactory
import com.example.homeproject.ui.screens.login.RegistrationViewModel
import com.example.homeproject.ui.screens.profile.Profile


class MainActivity : AppCompatActivity() {

    private lateinit var appComponent: AppComponent


   /* @Inject lateinit var registrationViewModel: RegistrationViewModel
    @Inject lateinit var chatsViewModel: ChatsViewModel
    @Inject lateinit var messageViewModelFactory: MessageViewModelFactory*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModule())
            .webSocketModule(WebSocketModule())
            .build()

        appComponent.inject(this)*/

        setContentView(R.layout.activity_main)


        /*setContent {

            //Navigation(registrationViewModel, chatsViewModel, messageViewModelFactory)
        }*/
    }
}

@Composable
fun Navigation(
    registrationViewModel: RegistrationViewModel,
    chatsViewModel: ChatsViewModel,
    messageViewModelFactory: MessageViewModelFactory
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "registration") {
        //composable("registration") { RegistrationPage.Registration(registrationViewModel, navController) }
        //composable("loginPage") { LoginPage.Login(registrationViewModel, navController) }
        composable("mainPage") { HomePage.HomeMenu(navController, chatsViewModel) }
        composable("profile") { Profile.profile(chatsViewModel, navController) }
        //"friends") { Friends.Friends(navController) }
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
