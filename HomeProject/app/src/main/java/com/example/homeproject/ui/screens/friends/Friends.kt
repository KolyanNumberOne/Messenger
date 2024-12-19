package com.example.homeproject.ui.screens.friends

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.homeproject.data.datamodels.Friend
import com.example.homeproject.ui.screens.home.HomePage
import com.example.homeproject.R


//@Preview(showBackground = true)
//@Composable
//fun PreviewReg4() {
//    val navController = rememberNavController()
//    Friends.Friends(navController = navController)
//}

object Friends{
    @Composable
    fun Friends(vm: FriendsViewModel, navController: NavHostController){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))) {
            TopBarFriends()
            SearchFriends(vm)
            Box(modifier = Modifier.weight(1F)){
                ListFriends(vm)
            }
            HomePage.BottomBar(navController = navController)
        }
    }

    @SuppressLint("ResourceType")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBarFriends(){
        TopAppBar(
            title = { Text("Друзья") },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = colorResource(id = R.color.topBar),
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )
    }

    @Composable
    fun SearchFriends(vm: FriendsViewModel){
        var search = remember { mutableStateOf(TextFieldValue("")) }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(56.dp)
            .clip(shape = RoundedCornerShape(22.dp))
            .background(color = colorResource(id = R.color.bottomBar)),
            contentAlignment = Alignment.CenterStart
        ){
            Row(verticalAlignment = Alignment.CenterVertically){
                OutlinedTextField(
                    value = search.value,
                    onValueChange = { search.value = it },
                    placeholder = { Text("Поиск...", color = Color.Gray, fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxSize(),
                    textStyle = TextStyle(fontSize = 14.sp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = colorResource(id = R.color.dialogs),
                        unfocusedTextColor = colorResource(id = R.color.dialogs),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        errorBorderColor = Color.Transparent
                    ),
                )
                Icon(Icons.Filled.Search,
                    contentDescription = "Поиск друзей",
                    modifier = Modifier
                        .padding(end = 15.dp).clickable { vm.searchUser(search.value.text) })
            }
        }
    }

    @Composable
    fun ListFriends(vm: FriendsViewModel) {

        val listFriend by vm.listFriend.collectAsState()
        val searchResults by vm.searchResults.collectAsState()


        Column {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.background))
            ) {
                item {
                    Text(
                        text = "Мои друзья",
                        modifier = Modifier
                            .padding(start = 5.dp, top = 10.dp)
                            .fillMaxWidth(),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(listFriend.size) { index ->
                    val friend = listFriend[index]
                    FriendItem(friend)
                }
                if (searchResults.isNotEmpty()) {
                    item {
                        Text(
                            text = "Глобальный поиск",
                            modifier = Modifier
                                .padding(start = 5.dp, top = 20.dp)
                                .fillMaxWidth(),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Список результатов глобального поиска
                    items(searchResults.size) { index ->
                        val searchResult = searchResults[index]
                        FriendItem(searchResult)
                    }
                }
            }
        }
    }

    @Composable
    fun FriendItem(friend: Friend) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(5.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = colorResource(id = R.color.dialogs)),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(60.dp)
                        .clip(shape = CircleShape)
                        .background(color = Color.Gray)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.user), // заменить на логику с изображением из базы
                        contentDescription = "Аватарка собеседника",
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.Unspecified
                    )
                }
                Text(
                    friend.userName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
        }
    }
}