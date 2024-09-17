package com.example.mvvmlogin.ui.login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvvmlogin.R
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Login(Modifier.align(Alignment.Center), viewModel)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel) {

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnabled: Boolean by viewModel.loginEnabled.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(modifier = modifier) {
            HeaderImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            EmailField(email) { viewModel.onLoginChange(it, password) }
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(password) { viewModel.onLoginChange(email, it) }
            Spacer(modifier = Modifier.padding(8.dp))
            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(loginEnabled){coroutineScope.launch { viewModel.onLoginSelected() }
            }
        }
    }
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.solar__login_3_bold_duotone),
        contentDescription = "Header Image",
        modifier = modifier.size(150.dp)

    )
}

@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit) {

    TextField(
        value = email,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color(0xFFDEDDDD),
            focusedTextColor = Color(0xFF636262)
        )
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {

    TextField(
        value = password,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFDEDDDD),
            focusedTextColor = Color(0xFF636262),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(text = "Forgot Password?",
        color = Color.Gray,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier.clickable { })
}

@Composable
fun LoginButton(loginEnabled: Boolean, onLoginSelected: () -> Unit) { //Single source of truth
    Button(
        onClick = { onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF4303),
            disabledContainerColor = Color(0xFFE0E0E0),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ), enabled = loginEnabled
    ) {
        Text(text = "Login", fontSize = 16.sp)
    }
}