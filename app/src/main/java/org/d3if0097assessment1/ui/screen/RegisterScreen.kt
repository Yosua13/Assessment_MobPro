package org.d3if0097assessment1.ui.screen

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.d3if0097assessment1.R
import org.d3if0097assessment1.navigation.Screen
import org.d3if0097assessment1.ui.theme.Assessment1Theme
import java.time.LocalDate
import java.util.Calendar
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(navHostController: NavHostController) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var noHP by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf("") }
    val options = listOf("Pengguna", "Gereja")
    var role by remember { mutableStateOf(options[0]) }
    var expanded by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }

    var firstNameError by remember { mutableStateOf(false) }
    var lastNameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var noHPError by remember { mutableStateOf(false) }
    var tanggalError by remember { mutableStateOf(false) }
    var roleError by remember { mutableStateOf(false) }

    var pickedDate by remember { mutableStateOf(LocalDate.now()) }

    val dateDialogState = rememberMaterialDialogState()

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            tanggal = selectedDate
        }, year, month, day
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(text = "Nama Depan") },
                singleLine = true,
                isError = firstNameError,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                shape = CircleShape
            )
            if (firstNameError) {
                Text(
                    text = "Nama Depan tidak boleh kosong atau lebih dari 25 karakter",
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(text = "Nama Belakang") },
                singleLine = true,
                isError = lastNameError,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                shape = CircleShape
            )
            if (lastNameError) {
                Text(
                    text = "Nama Belakang tidak boleh kosong atau lebih dari 10 karakter",
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Alamat Email") },
                singleLine = true,
                isError = emailError,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                shape = CircleShape
            )
            if (emailError) {
                Text(
                    text = "Alamat Surel tidak boleh kosong, menggunakan simbol kecuali @, atau lebih dari 30 karakter",
                    color = MaterialTheme.colorScheme.error
                )
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
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
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
                Text(
                    text = "Kata Sandi harus antara 6 dan 15 karakter",
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Konfirmasi Kata Sandi") },
                singleLine = true,
                isError = confirmPasswordError,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
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
            if (confirmPasswordError) {
                Text(
                    text = "Konfirmasi Kata Sandi tidak boleh kosong",
                    color = MaterialTheme.colorScheme.error
                )
            } else if (password != confirmPassword) {
                Text(
                    text = "Konfirmasi Kata Sandi Berbeda",
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = noHP,
                onValueChange = { noHP = it },
                label = { Text(text = "Nomor HP") },
                singleLine = true,
                isError = noHPError,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                shape = CircleShape
            )
            if (noHPError) {
                Text(
                    text = "Nomor HP harus angka dan tidak lebih dari 13 karakter",
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = tanggal,
                onValueChange = { tanggal = it },
                readOnly = true,
                singleLine = true,
                isError = tanggalError,
                label = { Text("Tanggal Lahir") },
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Pilih Tanggal")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                shape = CircleShape
            )
            if (tanggalError) {
                Text(
                    text = "Tanggal Lahir tidak boleh kosong", color = MaterialTheme.colorScheme.error
                )
            }
            // Dialog untuk pemilih tanggal
            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(text = "OK") {
                        dateDialogState.hide()
                    }
                    negativeButton(text = "Batal") {
                        dateDialogState.hide()
                    }
                }
            ) {
                datepicker(
                    initialDate = pickedDate,
                    onDateChange = { pickedDate = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    isError = roleError,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .imePadding(),
                    shape = CircleShape,
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            text = { Text(text = text) },
                            onClick = {
                                role = options[index]
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
            if (roleError) {
                Text(text = "tidak boleh kosong", color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    firstNameError = firstName.isEmpty() || firstName.length > 25 || firstName.length <= 3
                    lastNameError = lastName.isEmpty() || lastName.length > 10 || lastName.length <= 3
                    emailError = email.isEmpty() || email.length > 30 || email.any { !it.isLetterOrDigit() && it != '@' && it == '_' && it == '.' }
                    passwordError = password.isEmpty() || password.length < 6 || password.length > 15
                    confirmPasswordError = confirmPassword.isEmpty() || confirmPassword != password
                    noHPError = noHP.isEmpty() || noHP.length > 13 || noHP.any { !it.isDigit() }
                    tanggalError = tanggal.isEmpty()
                    roleError = role.isEmpty()

                    if (password != confirmPassword) {
                        confirmPasswordError = true
                        Toast.makeText(context, "Password tidak sama", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }

                    if (!firstNameError && !lastNameError && !emailError && !passwordError && !confirmPasswordError && !noHPError && !tanggalError && !roleError) {
                        navHostController.navigate(Screen.Login.route)
                        Toast.makeText(context, "Pendaftaran akun berhasil", Toast.LENGTH_SHORT).show()
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
                Text("Daftar")
            }
            Row(modifier = Modifier.padding(start = 60.dp)) {
                Text(
                    text = stringResource(R.string.have_account),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 14.dp),
                )
                TextButton(
                    onClick = {
                        navHostController.navigate(Screen.Login.route)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.login_here),
                        fontSize = 14.sp,
                        color = Color.Blue
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    Assessment1Theme {
        RegisterScreen(rememberNavController())
    }
}