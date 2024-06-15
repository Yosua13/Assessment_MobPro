package org.d3if0097assessment1.ui.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
import org.d3if0097assessment1.viewmodel.BeritaViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

const val KEY_ID_BERITA = "id"

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
            context = context,
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
    context: Context,
    modifier: Modifier,
) {

    val focusManager = LocalFocusManager.current
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    var pickedTime by remember { mutableStateOf(LocalTime.now()) }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

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
            onTanggalChange(selectedDate)
        }, year, month, day
    )

    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
            onJamChange(selectedTime)
        }, currentHour, currentMinute, true
    )
    var expanded by remember { mutableStateOf(false) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = todo,
            onValueChange = { onTodoChange(it) },
            label = { Text(text = "Nama Acara") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

        OutlinedTextField(
            value = tanggal,
            onValueChange = { onTanggalChange(it) },
            readOnly = true,
            singleLine = true,
            label = { Text("Jadwal Ibadah Gereja") },
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Pilih Tanggal"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

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

        OutlinedTextField(
            value = jam,
            onValueChange = { onJamChange(it) },
            readOnly = true,
            singleLine = true,
            label = { Text("Jam Ibadah Gereja") },
            trailingIcon = {
                IconButton(onClick = { timePickerDialog.show() }) {
                    Icon(imageVector = Icons.Default.AccessTime, contentDescription = "Pilih Jam")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

        // Dialog untuk pemilih jam
        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(text = "OK") {
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
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                })
        }
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