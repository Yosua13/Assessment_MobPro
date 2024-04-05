package org.d3if0097assessment1.ui.screen

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0097assessment1.R
import org.d3if0097assessment1.navigation.Screen
import org.d3if0097assessment1.ui.theme.Assessment1Theme

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        Image(
            painter = painterResource(R.drawable.login_screen),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
        )
        Text(
            text = stringResource(R.string.welcome_back),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 48.dp)
        )
        Text(
            text = stringResource(R.string.login_account),
            modifier = Modifier
                .padding(top = 8.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { newEmail ->
                email = newEmail
                emailError = newEmail.isNullOrEmpty() == null
            },
            label = {
                Text(text = stringResource(R.string.email_address))
            },
            isError = emailError,
            trailingIcon = { IconPicker(emailError) },
            supportingText = { ErrorHint(emailError) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { newPassword ->
                password = newPassword
                passwordError = newPassword.isNullOrEmpty() == null
            },
            label = {
                Text(text = stringResource(R.string.password))
            },
            isError = passwordError,
            trailingIcon = { IconPicker(passwordError) },
            supportingText = { ErrorHint(passwordError) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = {
                emailError = email.isEmpty()
                passwordError = password.isEmpty()
                if (emailError || passwordError) {
                    return@Button
                }
                val message = "Hi, Selamat Datang di Gereja Ku"
                navHostController.navigate(Screen.Home.route)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
        ) {
            Text(
                text = stringResource(R.string.login),
                fontSize = 18.sp,
                modifier = Modifier.padding(4.dp)
            )
        }
        Row(
        ) {
            Text(
                text = stringResource(R.string.dont_have_account),
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 14.dp),
            )
            TextButton(
                onClick = {
                    navHostController.navigate(Screen.Register.route)
                }
            ) {
                Text(
                    text = stringResource(R.string.register_here),
                    fontSize = 14.sp,
                    color = Color.Blue
                )
            }
        }
        if (email.isNotEmpty() && password.isNotEmpty()) {
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            Text(
                text = email,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = password,
                style = MaterialTheme.typography.titleLarge
            )
            Button(
                onClick = {
                    shareData(context, email, password)
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.bagikan))
            }
        }
    }
}

private fun shareData(context: Context, email: String, password: String) {
    val message = "Email: $email\nPassword: $password"
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Composable
fun IconPicker(isError: Boolean) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    Assessment1Theme {
        LoginScreen(rememberNavController())
    }
}