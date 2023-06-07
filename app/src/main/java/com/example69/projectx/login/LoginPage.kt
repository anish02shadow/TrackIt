package com.example69.projectx.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example69.projectx.login.LoginViewModel

@Composable
fun LoginPage1(onNavToHomePage:() -> Unit,
               onNavToSignUpPage:() -> Unit,) {

    Box(){
        BgCard()
        MainCard(onNavToHomePage=onNavToHomePage, onNavToSignUpPage = onNavToSignUpPage)
    }

}
@Composable
fun test(){

}
@Composable
fun BgCard() {
    val purplish = Color(0xff4e3d77)
    val orangish = Color(0xffffac00)
    val signupText = buildAnnotatedString {
        append("Don't have an account? ")
        withStyle(SpanStyle(color = orangish)) {
            append("Sign up here!")
        }
    }
    Surface(color = purplish, modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.offset(y = (-30).dp)
        ) {
            Row() {
                Image(painter = painterResource(id = com.example69.projectx.R.drawable.ic_google), contentDescription ="google" )

            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(text = signupText, color = Color.White)
        }
    }
}


@OptIn(ExperimentalTextApi::class)
@Composable
fun MainCard(loginViewModel: LoginViewModel? = null,
             onNavToHomePage:() -> Unit,
             onNavToSignUpPage:() -> Unit,
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current
    val purplish = Color(0xff4e3d77)
    val orangish = Color(0xffffac00)
    val emailState = remember { mutableStateOf(TextFieldValue("mtechviral@gmail.com")) }
    val passState = remember { mutableStateOf(TextFieldValue("")) }
    Surface(
        color = Color(0xFFF3EAD8), modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {

        Column(
            modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)

            //Image(painter = painterResource(id = R.drawable.trackitlogin), contentDescription ="google" )
            //modifier= Modifier.fillMaxWidth().fillMaxHeight(0.3f)
            Spacer(modifier = Modifier.height(15.dp))
            val gradientColors = listOf(Color(0xFFA086DF), Color(0xFFF0C66C), Color(0xFFE0B34F))
            Text(
                text = "Hey,",
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                ),
                fontSize = 45.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 5.dp)
            )

            Text(
                text = "Login Now",
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                ),
                fontSize = 40.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 5.dp)
            )

            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                value = loginUiState?.userName ?: "",
                onValueChange = {loginViewModel?.onUserNameChange(it)},
                label = { Text("Email address") },
                leadingIcon = { Icons.Filled.Email },
                modifier = modifier,
                isError = isError
            )

            Spacer(modifier = Modifier.padding(6.dp))
            OutlinedTextField(
                value = loginUiState?.password ?: "",
                onValueChange = { loginViewModel?.onPasswordNameChange(it) },
                label = { Text("Password") },
                leadingIcon = { Icons.Filled.Lock },
                visualTransformation = PasswordVisualTransformation(),
                isError = isError
            )

            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                Text(text = "Forgot password?", textAlign = TextAlign.End, modifier = modifier)
            }
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            Button(
                onClick = {loginViewModel?.loginUser(context)},
                colors = ButtonDefaults.buttonColors(backgroundColor = orangish),
                shape = MaterialTheme.shapes.medium,
                modifier = modifier,
                contentPadding = PaddingValues(14.dp)
            )
            {
                Text(text = "Log In", color = Color.White)

                if (isError) {
                    Text(
                        text = loginUiState?.loginError ?: "unknown error",
                        color = Color.Red,
                    )
                }
            }
        }
        if (loginUiState?.isLoading == true){
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.hasUser){
            if (loginViewModel?.hasUser == true){
                onNavToHomePage.invoke()
            }
        }
    }
}