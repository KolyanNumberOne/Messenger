package com.example.homeproject.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.homeproject.ui.screens.chat.ChatsViewModel
import com.example.homeproject.R

object HomePage{
    @Composable
    fun HomeMenu(navController: NavHostController, viewModel: ChatsViewModel){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            TopBar()
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) { Chats(navController, viewModel) }
            Divider(color = Color.Black, thickness = 1.dp)
            BottomBar(navController)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar(){
        var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
        var isSearching by remember { mutableStateOf(false) }

        TopAppBar(
            title = {
                if (isSearching) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Поиск...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
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
                        textStyle = TextStyle(fontSize = 20.sp)
                    )
                } else {
                    Text("MyMess")
                }
            },
            actions = {
                if (isSearching) {
                    IconButton(onClick = {
                        isSearching = false
                        searchQuery = TextFieldValue("")
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Закрыть поиск")
                    }
                } else {
                    IconButton(onClick = { isSearching = true }) {
                        Icon(Icons.Default.Search, contentDescription = "Поиск")
                    }
                }
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = colorResource(id = R.color.topBar),
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
    @Composable
    fun BottomBar(navController: NavHostController){
        BottomAppBar(modifier = Modifier.height(70.dp), containerColor = colorResource(id = R.color.bottomBar)){
            Row(modifier = Modifier.fillMaxSize(),  verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly){
                IconButton(onClick = { navController.navigate("friends") }, modifier = Modifier
                    .width(75.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = colorResource(id = R.color.bottomBarButtons))) {
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Face, contentDescription = "Поиск")
                        Text(text = "Друзья", style = TextStyle(fontSize = 9.sp))
                    }
                }
                IconButton(onClick = { navController.navigate("mainPage")}, modifier = Modifier
                    .width(75.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = colorResource(id = R.color.bottomBarButtons))) {
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Send, contentDescription = "Поиск")
                        Text(text = "Диалоги", style = TextStyle(fontSize = 9.sp))
                    }
                }
                IconButton(onClick = { navController.navigate("profile") }, modifier = Modifier
                    .width(75.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = colorResource(id = R.color.bottomBarButtons))) {
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Person, contentDescription = "Поиск")
                        Text(text = "Профиль", style = TextStyle(fontSize = 9.sp))
                    }
                }
            }
        }
    }

    @Composable
    fun Chats(navController: NavHostController, viewModel: ChatsViewModel){
        val listDialogues by viewModel.listDialogues.collectAsState()

        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))){
            items(listDialogues.size) { id ->
                val dialog = listDialogues[id]
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(5.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(color = colorResource(id = R.color.dialogs))
                        .clickable { navController.navigate("chat/${dialog.id}") },
                ) {
                    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically){
                        Box(modifier = Modifier
                            .padding(start = 10.dp)
                            .size(60.dp)
                            .clip(shape = CircleShape)
                            .background(color = Color.Gray)){
                            Icon(painter = painterResource(id = R.drawable.user), contentDescription = "Аватарка собеседника", modifier = Modifier.fillMaxSize(), tint = Color.Unspecified)
                        }
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 15.dp, end = 15.dp, top = 10.dp)){
                            Text(dialog.nameFriend, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(dialog.lastMessage,color = Color.LightGray, maxLines = 1, modifier = Modifier.padding(top = 5.dp), fontSize = 14.sp, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
            }
        }
    }
}