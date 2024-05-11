package org.d3if0097assessment1.ui.screen

import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.d3if0097assessment1.R
import org.d3if0097assessment1.database.BukuDb
import org.d3if0097assessment1.ui.theme.Assessment1Theme
import org.d3if0097assessment1.util.ViewModelFactoryBerita
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeritaDetailScreen(
    navHostController: NavHostController,
    id: Long? = null,
) {
    val context = LocalContext.current
    val db = BukuDb.getInstance(context)
    val factory = ViewModelFactoryBerita(db.beritaDao)
    val viewModel: BeritaViewModel = viewModel(factory = factory)

    var todo by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf("") }
    var jam by remember { mutableStateOf("") }

    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    var pickedTime by remember { mutableStateOf(LocalTime.NOON) }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("hh:mm").format(pickedTime)
        }
    }


    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getBerita(id) ?: return@LaunchedEffect
        todo = data.todo
        tanggal = data.tanggal
        jam = data.jam
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(R.string.tambahan_buku))
                    else
                        Text(text = stringResource(R.string.edit_buku))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        if (todo == "" || tanggal == "" || jam == "") {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(todo, tanggal, jam)
                        } else {
                            viewModel.update(id, todo, tanggal, jam)
                        }
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            viewModel.delete(id)
                            navHostController.popBackStack()
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormBerita(
            todo = todo,
            onTodoChange = { todo = it },
            tanggal = tanggal,
            onTanggalChange = { tanggal = it },
            jam = jam,
            onJamChange = { jam = it },
            modifier = Modifier.padding(padding)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun FormBerita(
    todo: String,
    onTodoChange: (String) -> Unit,
    tanggal: String,
    onTanggalChange: (String) -> Unit,
    jam: String,
    onJamChange: (String) -> Unit,
    modifier: Modifier,
) {
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    var pickedTime by remember { mutableStateOf(LocalTime.NOON) }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("hh:mm").format(pickedTime)
        }
    }

    // Memastikan tanggal dan jam diperbarui di luar komposisi saat pickedDate atau pickedTime berubah
    LaunchedEffect(pickedDate) {
        onTanggalChange(formattedDate)
    }

    LaunchedEffect(pickedTime) {
        onJamChange(formattedTime)
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = todo,
            onValueChange = { onTodoChange(it) },
            label = { Text(text = stringResource(R.string.todo)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = tanggal,
            onValueChange = {}, // Tidak mengizinkan pengguna mengubah nilai secara manual
            label = { Text(text = stringResource(R.string.tanggal)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = jam,
            onValueChange = {}, // Tidak mengizinkan pengguna mengubah nilai secara manual
            label = { Text(text = stringResource(R.string.waktu)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                dateDialogState.show()
            }) {
                Text(text = "Pilih Tanggal")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = formattedDate)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                timeDialogState.show()
            }) {
                Text(text = "Pilih Waktu")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = formattedTime)
        }
    }

    // Dialog untuk pemilih tanggal
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "OK") {
                // Simpan tanggal terpilih dan perbarui tampilan
                onTanggalChange(DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedDate))
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

    // Dialog untuk pemilih waktu
    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = "OK") {
                // Simpan waktu terpilih dan perbarui tampilan
                onJamChange(DateTimeFormatter.ofPattern("hh:mm").format(pickedTime))
                timeDialogState.hide()
            }
            negativeButton(text = "Batal") {
                timeDialogState.hide()
            }
        }
    ) {
        timepicker(
            initialTime = pickedTime,
            onTimeChange = { pickedTime = it }
        )
    }
}


@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun BeritaDetailScreenPreview() {
    Assessment1Theme {
        BeritaDetailScreen(rememberNavController())
    }
}