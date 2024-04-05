package org.d3if0097assessment1.ui.screen

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

enum class Gender {
    Pria,
    Wanita
}

@Composable
fun RegisterScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var nameError by rememberSaveable { mutableStateOf(false) }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
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
            text = stringResource(R.string.register_account),
            modifier = Modifier
                .padding(top = 8.dp)
        )
        OutlinedTextField(
            value = name,
            onValueChange = { newName ->
                name = newName
                nameError = newName.isNullOrEmpty() == null
            },
            label = {
                Text(text = stringResource(R.string.name))
            },
            isError = nameError,
            trailingIcon = { IconPickerr(nameError) },
            supportingText = { ErrorHintr(nameError) },
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
            value = email,
            onValueChange = { newEmail ->
                email = newEmail
                emailError = newEmail.isNullOrEmpty() == null
            },
            label = {
                Text(text = stringResource(R.string.email_address))
            },
            isError = emailError,
            trailingIcon = { IconPickerr(emailError) },
            supportingText = { ErrorHintr(emailError) },
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
            trailingIcon = { IconPickerr(passwordError) },
            supportingText = { ErrorHintr(passwordError) },
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
        ExposedDropdownMenuBox()
        Button(
            onClick = {
                nameError = name.isEmpty()
                emailError = email.isEmpty()
                passwordError = password.isEmpty()
                if (emailError || passwordError || nameError) return@Button
            },
            modifier = Modifier
        ) {
            Text(
                text = stringResource(R.string.daftar),
                fontSize = 18.sp,
                modifier = Modifier.padding(4.dp)
            )
        }
        Row(
        ) {
            Text(
                text = stringResource(R.string.have_account),
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 14.dp),
            )
            TextButton(
                onClick = {
                    navHostController.popBackStack()
                }
            ) {
                Text(
                    text = stringResource(R.string.register_here),
                    fontSize = 14.sp,
                    color = Color.Blue
                )
            }
        }
        if (email.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge
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
                    shareData(context, name, email, password)
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.bagikan))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBox() {
    val context = LocalContext.current
    val gender = arrayOf("Pria", "Wanita")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(gender[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 8.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                gender.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

private fun shareData(context: Context, name: String, email: String, password: String) {
    val message = "Nama: $name\nEmail: $email\nPassword: $password"
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Composable
fun IconPickerr(isError: Boolean) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    }
}

@Composable
fun ErrorHintr(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input))
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    Assessment1Theme {
        RegisterScreen(rememberNavController())
    }
}