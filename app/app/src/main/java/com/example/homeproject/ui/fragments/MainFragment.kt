package com.example.homeproject.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homeproject.ui.screens.chat.ChatsViewModel
import com.example.homeproject.data.datamodels.token
import com.example.homeproject.ui.screens.friends.FriendsViewModel
import com.example.homeproject.ui.screens.chat.MessageViewModelFactory
import com.example.homeproject.R
import com.example.homeproject.dagger2.components.AppComponent
import com.example.homeproject.dagger2.components.DaggerAppComponent
import com.example.homeproject.dagger2.modules.AppModule
import com.example.homeproject.ui.navigation.MainNavigation
import javax.inject.Inject

class MainFragment: Fragment() {

    private lateinit var appComponent: AppComponent

    @Inject lateinit var chatsViewModel: ChatsViewModel
    @Inject lateinit var messageViewModelFactory: MessageViewModelFactory
    @Inject lateinit var friendsViewModel: FriendsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MainNavigation(chatsViewModel, messageViewModelFactory,friendsViewModel, onAuthPageClick = {
                    findNavController().navigate(R.id.action_mainFragment_to_authFragment) {
                        popUpTo(R.id.mainFragment) { inclusive = true }
                    }
                })
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(requireActivity().application))
            .build()

        appComponent.inject(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainFragment", "Фрагмент уничтожен")
        token.clearToken()
    }
}
