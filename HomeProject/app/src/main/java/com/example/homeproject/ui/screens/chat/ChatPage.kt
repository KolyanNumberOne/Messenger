package com.example.homeproject.ui.screens.chat

import android.view.ViewTreeObserver
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.twotone.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.homeproject.data.datamodels.Message
import com.example.homeproject.R
import com.example.homeproject.data.datamodels.token
import com.example.homeproject.ui.utilities.getBitmapFromResource


@Preview(showBackground = true)
@Composable
fun PreviewReg7() {
    val navController = rememberNavController()
    //ChatPage.Chat()
}

object ChatPage {
    @Composable
    fun Chat(vm: MessageViewModel, navController: NavController) {
        val listState = rememberLazyListState()

        val listMessage by vm.listMessage.collectAsState()

        val view = LocalView.current
        val viewTreeObserver = view.viewTreeObserver
        var isKeyboardOpen by remember { mutableStateOf(false) }
        DisposableEffect(viewTreeObserver) {
            val listener = ViewTreeObserver.OnGlobalLayoutListener {
                isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                    ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true

            }

            viewTreeObserver.addOnGlobalLayoutListener(listener)
            onDispose {
                viewTreeObserver.removeOnGlobalLayoutListener(listener)
            }
        }

        LaunchedEffect(isKeyboardOpen) {
            listState.animateScrollToItem(listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.background))
        ) {
            TopBarChat(navController = navController)
            Box(modifier = Modifier.weight(1f)) {
                Messages(listMessage, listState)
            }
            EnterMessage(listState, vm)
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBarChat(navController: NavController) {
        val context = LocalContext.current
        val bitmap = getBitmapFromResource(context, R.drawable.user).asImageBitmap()
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = "Аватарка",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(shape = CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        "Юзер",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Назад")
                }
            },
            actions = {
                Icon(Icons.Filled.MoreVert, contentDescription = null)
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = colorResource(id = R.color.topBar),
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            )
        )
    }

    @Composable
    fun Messages(listMessage : List<Message>, listState : LazyListState) {
        val idUser = token.id
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp)
                .systemBarsPadding()
        ) {
            items(listMessage.size) { index ->
                val message = listMessage[index]
                val color = if (idUser == message.id_sender) colorResource(id = R.color.messUser) else colorResource(id = R.color.dialogs)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (idUser == message.id_sender) Arrangement.End else Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .widthIn(max = 300.dp)
                            .padding(vertical = 8.dp)
                            .background(
                                color = color,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            // Текст сообщения
                            Text(
                                text = message.message,
                                fontSize = 16.sp,
                                color = Color.White
                            )

                            // Время
                            Text(
                                text = message.timeMessage,
                                fontSize = 12.sp,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }
        LaunchedEffect(listMessage.size) {
            listState.animateScrollToItem(listMessage.size) //-1 добавить
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EnterMessage(listState : LazyListState, vm: MessageViewModel) {
        var message by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.bottomBar)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = message,
                onValueChange = { message = it },
                placeholder = { Text("Введите сообщение...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp, end = 10.dp),
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
            Button(
                onClick = {
                    vm.addMessage(message)
                    message = ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black,
                    disabledContentColor = Color.White,
                    disabledContainerColor = Color.Transparent
                ),
                enabled = !message.isEmpty()
            ) {
                Icon(
                    Icons.TwoTone.Send,
                    contentDescription = "Отправить",
                )
            }
        }
    }
}

