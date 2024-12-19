package com.example.homeproject.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.homeproject.ui.screens.chat.ChatsViewModel
import com.example.homeproject.ui.screens.home.HomePage
import com.example.homeproject.R

/*@Preview(showBackground = true)
@Composable
fun PreviewProfile() {
    val viewModel = remember {
        ChatsViewModel(Application())
    }
    val navController = rememberNavController()
    Profile.profile(viewModel, navController)
}*/

object Profile {
    @Composable
    fun profile(vm: ChatsViewModel, navController: NavHostController){
        val bitmap = vm.imageBitmap.value
        Column {
            Column(modifier = Modifier
                .weight(1F)
                .fillMaxSize()
                .background(color = colorResource(id = R.color.background)),
                horizontalAlignment = Alignment.CenterHorizontally){
                Spacer(modifier = Modifier.size(80.dp))
                // Проверка на null перед использованием Bitmap
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Изображение из ViewModel",
                        modifier = Modifier
                            .size(300.dp)
                            .padding(20.dp)
                            .clip(shape = CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                else{
                    Icon(Icons.Filled.AccountCircle, contentDescription = "ImageProfile", modifier = Modifier
                        .size(300.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .clickable {  })
                }
                Box(modifier = Modifier
                    .size(width = 300.dp, height = 400.dp)
                    .border(
                        width = 3.dp,
                        brush = SolidColor(colorResource(id = R.color.bottomBar)),
                        shape = RectangleShape
                    ) ){
                    Column {
                        val commonTextStyle = TextStyle(
                            color = colorResource(id = R.color.dialogs),
                            fontSize = 24.sp
                        )
                        Text("Имя пользователя", style = commonTextStyle, modifier = Modifier.padding(start = 5.dp, top = 15.dp), textDecoration = TextDecoration.Underline)
                        Text("Коя Бес", style = commonTextStyle, modifier = Modifier.padding(start = 5.dp, top = 5.dp))
                        Text("Количество друзей", style = commonTextStyle, modifier = Modifier.padding(start = 5.dp, top = 15.dp), textDecoration = TextDecoration.Underline)
                        Text("5", style = commonTextStyle, modifier = Modifier.padding(start = 5.dp, top = 5.dp))
                    }
                }
            }
            val lifecycleOwner = LocalLifecycleOwner.current

            DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_PAUSE || event == Lifecycle.Event.ON_DESTROY) {
                    }
                }

                lifecycleOwner.lifecycle.addObserver(observer)

                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }
            HomePage.BottomBar(navController = navController)
        }
    }
}
