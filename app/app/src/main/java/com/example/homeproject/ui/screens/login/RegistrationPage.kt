package com.example.homeproject.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.homeproject.R


@Preview(showBackground = true)
@Composable
fun PreviewReg() {
    // Создаем ViewModel вручную для Preview
    //val viewModel = RegistrationViewModel(Application())
    val navController = rememberNavController()
    //RegistrationPage.Registration(viewModel, navController)
}

object RegistrationPage {
    @Composable
    fun Registration(vm: RegistrationViewModel, navController: NavHostController, onMainPageClick: () -> Unit){
        Column(
            Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.background))
                .offset(y = 150.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Create Account",  style = TextStyle(fontSize = 30.sp), color = Color.Blue)
            Vvod(vm)
            Reg(navController = navController, vm, onMainPageClick)
            RegToEnter(navController = navController)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Vvod(vm: RegistrationViewModel){
        var login = vm.login.observeAsState("")
        var password = vm.password.observeAsState("")
        var confirmPassword = vm.confirmPassword.observeAsState("")
        val fields = listOf(
            Triple( login, { newValue: String -> vm.updateLogin(newValue) }, "Введите имя пользователя"),
            Triple(password, { newValue: String -> vm.updatePassword(newValue) },"Введите пароль"),
            Triple(confirmPassword, { newValue: String -> vm.updateConfirmPassword(newValue) },"Подтвердите пароль")
        )

        Column {
            fields.forEach { field ->
                Box(
                    Modifier
                        .height(80.dp)
                        .width(300.dp)
                        .padding(top = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color = Color.White)
                ) {
                    TextField(
                        value = field.first.value,
                        onValueChange = field.second,
                        modifier = Modifier.fillMaxSize(),
                        label = { Text(field.third) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(id = R.color.bottomBar),
                            unfocusedContainerColor = colorResource(id = R.color.bottomBar),
                            disabledContainerColor = colorResource(id = R.color.bottomBar),
                            errorContainerColor = colorResource(id = R.color.bottomBar)
                        )
                    )
                }
            }
        }
    }
    @Composable
    fun Reg(navController: NavHostController, vm: RegistrationViewModel, onMainPageClick: () -> Unit){
        val errorMessage by vm.errorMessage.observeAsState("")
        Column(modifier = Modifier
            .width(300.dp)
            .height(100.dp)
            .offset(x = 0.dp, y = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally){
            if (errorMessage.isNotEmpty()) {
                Box(modifier = Modifier.height(20.dp)){
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                    )
                }
            }
            else {
                Spacer(modifier = Modifier.size(20.dp))
            }
            Button(onClick = {
                if (vm.checkUserToReg()){
                    onMainPageClick()
                } },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dialogs))
            ) {
                Text("Зарегистрироваться")
            }
        }
    }

    @Composable
    fun RegToEnter(navController: NavHostController){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Уже есть аккаунт?")
            Text("Войти", color = Color(0xFF0077C0),textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {navController.navigate("loginPage") }
            )
        }
    }
}