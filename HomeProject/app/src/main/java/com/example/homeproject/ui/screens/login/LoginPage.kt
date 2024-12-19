package com.example.homeproject.ui.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.homeproject.R

@SuppressLint("RememberReturnType")
@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    val viewModel = remember {
    }
    val navController = rememberNavController()
    //LoginPage.Login(viewModel, navController)
}

object LoginPage {
    @Composable
    fun Login(vm: LoginViewModel, navController: NavHostController, onMainPageClick: () -> Unit){
        Column(
            Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.background))
                .offset(y = 150.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "ДОБРО ПОЖАЛОВАТЬ",  style = TextStyle(fontSize = 30.sp), color = Color.Blue)
            Spacer(modifier = Modifier
                .height(20.dp)
                .fillMaxWidth())
            UserNameAndPassword(vm = vm)
            Vxod(vm = vm, navController = navController, onMainPageClick)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UserNameAndPassword(vm: LoginViewModel){
        val login = vm.login.observeAsState("")
        val password = vm.password.observeAsState("")
        val errorMessage by vm.errorMessage.observeAsState("")
        val fields = listOf(
            Triple(login, { newValue: String -> vm.updateLogin(newValue) }, "Введите логин"),
            Triple(password, { newValue: String -> vm.updatePassword(newValue) },"Введите пароль"),
        )

        Column (
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            fields.forEach { field ->
                TextField(
                    value = field.first.value,
                    onValueChange = field.second,
                    Modifier
                        .height(80.dp)
                        .width(300.dp)
                        .padding(top = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color = Color.Black),
                    label = { Text(field.third) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(id = R.color.bottomBar),
                        unfocusedContainerColor = colorResource(id = R.color.bottomBar),
                        disabledContainerColor = colorResource(id = R.color.bottomBar),
                        errorContainerColor = colorResource(id = R.color.bottomBar)
                    )
                )
            }
            if (errorMessage.isNotEmpty()) {
                Box(modifier = Modifier.height(20.dp).offset(y = 10.dp)){
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                    )
                }
            }
            else {
                Spacer(modifier = Modifier.size(20.dp))
            }
        }
    }


    @Composable
    fun Vxod(vm: LoginViewModel, navController: NavHostController, onMainPageClick: () -> Unit){
        Column(modifier = Modifier
            .width(300.dp)
            .height(100.dp)
            .offset(x = 0.dp, y = 20.dp),
            verticalArrangement = Arrangement.Center){
            Button(onClick = {
                if(vm.checkUser()) {onMainPageClick()} },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dialogs)))
            {
                Text("Войти")
            }
            Button(onClick = { navController.navigate("registration") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dialogs)))
            {
                Text("Регистрация")
            }
        }
    }
}