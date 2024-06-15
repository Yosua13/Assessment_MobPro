package org.d3if0097assessment1.ui.screen

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0097assessment1.R
import org.d3if0097assessment1.model.User
import org.d3if0097assessment1.navigation.Screen
import org.d3if0097assessment1.navigation.UserDataStore
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
    val dataStore = UserDataStore(context)
    val user by dataStore.userFlow.collectAsState(User())
    var showDialog by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
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
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            isError = emailError,
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            shape = CircleShape
        )
        if (emailError) {
            Text(text = "Email tidak boleh kosong", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Kata Sandi") },
            singleLine = true,
            isError = passwordError,
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            shape = CircleShape,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(
                        imageVector = image,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            }
        )
        if (passwordError) {
            Text(text = "Password tidak boleh kosong", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                emailError = email.isEmpty()
                passwordError = password.isEmpty()
                if (emailError || passwordError) {
                    return@Button
                }

                if (!emailError && !passwordError) {
                    Toast.makeText(context, "Hi, Selamat Datang di Gereja Ku", Toast.LENGTH_SHORT).show()
                    navHostController.navigate(Screen.Buku.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(top = 8.dp, bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(android.graphics.Color.parseColor("#7d32a8"))),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = stringResource(R.string.login),
                fontSize = 18.sp,
                modifier = Modifier.padding(4.dp)
            )
        }
        Row {
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
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Atau masuk dengan akun google")
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(R.drawable.google),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    if (user.email.isEmpty()) {
                        CoroutineScope(Dispatchers.IO).launch {
                            signIn(
                                context,
                                dataStore,
                                navHostController
                            )
                        }
                    } else {
                        showDialog = true
                    }
                }
        )
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


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    Assessment1Theme {
        LoginScreen(rememberNavController())
    }
}