package org.d3if0097assessment1.ui.screen

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0097assessment1.R
import org.d3if0097assessment1.database.BukuDb
import org.d3if0097assessment1.ui.theme.Assessment1Theme
import org.d3if0097assessment1.util.ViewModelFactoryBuku
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

const val KEY_ID_BUKU = "id"
const val KEY_ID_BERITA = "id"

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BukuDetailScreen(
    navHostController: NavHostController,
    id: Long? = null,
) {
    val context = LocalContext.current
    val db = BukuDb.getInstance(context)
    val factory = ViewModelFactoryBuku(db.bukuDao)
    val viewModel: BukuViewModel = viewModel(factory = factory)

    var judul by remember { mutableStateOf("") }
    var penulis by remember { mutableStateOf("") }
    var sinopsis by remember { mutableStateOf("") }
    var gambarUri by remember { mutableStateOf<Uri?>(null) } // Tipe data Uri? untuk gambar
    var selectedGambarUri by remember { mutableStateOf<Uri?>(null) }
    // Callback to save selected image URI to database
    val onGambarSelected: (Uri) -> Unit = { uri ->
        selectedGambarUri = uri
        val resourceId = saveImageToInternalStorage(context, uri)
        resourceId?.let { resourceId ->
            if (id == null) {
                viewModel.insert(judul, penulis, sinopsis, resourceId.toString())
            } else {
                viewModel.update(id, judul, penulis, sinopsis, resourceId.toString())
            }
        }
    }

    LaunchedEffect(true) {
        if (id != null) {
            val data = viewModel.getBuku(id) ?: return@LaunchedEffect
            judul = data.judul
            penulis = data.penulis
            sinopsis = data.sinopsis
            // Mengatur gambarUri ke Uri yang valid dari data.gambar (jika data.gambar adalah URI yang valid)
            gambarUri = Uri.parse(data.gambar)
        }
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
                        if (judul.isBlank() || penulis.isBlank() || sinopsis.isBlank() || gambarUri == null) {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_SHORT).show()
                        } else {
                            // Call onGambarSelected to save the selected image to database
                            gambarUri?.let { uri ->
                                onGambarSelected(uri)
                            }
                            navHostController.popBackStack()
                        }
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
        FormBuku(
            judul = judul,
            onJudulChange = { judul = it },
            penulis = penulis,
            onPenulisChange = { penulis = it },
            sinopsis = sinopsis,
            onSinopsisChange = { sinopsis = it },
            onGambarSelected = onGambarSelected,
            modifier = Modifier.padding(padding)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun FormBuku(
    judul: String,
    onJudulChange: (String) -> Unit,
    penulis: String,
    onPenulisChange: (String) -> Unit,
    sinopsis: String,
    onSinopsisChange: (String) -> Unit,
    onGambarSelected: (Uri) -> Unit, // Callback untuk URI gambar terpilih
    modifier: Modifier,
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { selectedUri ->
            onGambarSelected(selectedUri) // Panggil callback dengan URI gambar terpilih
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            value = judul,
            onValueChange = { onJudulChange(it) },
            label = { Text(text = stringResource(R.string.judul)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = penulis,
            onValueChange = { onPenulisChange(it) },
            label = { Text(text = stringResource(R.string.penulis)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = sinopsis,
            onValueChange = { onSinopsisChange(it) },
            label = { Text(text = stringResource(R.string.sinopsis)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        // Tambahkan ImageButton untuk memilih gambar
        ImageButton(
            onClick = { launcher.launch("image/*") }, // Buka galeri saat tombol ditekan
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(width = 1.dp, color = Color.Gray, shape = MaterialTheme.shapes.medium),
            text = "Pilih Gambar" // Teks yang ditampilkan di sebelah ikon
        )

        // Tampilkan gambar terpilih (jika ada)
        imageUri?.let { uri ->
            // Menggunakan kotlin.io.use untuk menutup InputStream otomatis setelah digunakan
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                // Mendeklarasikan bitmap baru
                val decodedBitmap: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
                } else {
                    BitmapFactory.decodeStream(inputStream)
                }

                // Mengatur nilai bitmap state dengan decodedBitmap
                bitmap.value = decodedBitmap

                // Memeriksa apakah bitmap memiliki nilai yang tidak null
                bitmap.value?.let { bitmap ->
                    // Menampilkan gambar menggunakan Image composable
                    Image(
                        bitmap = bitmap.asImageBitmap(), // Mengonversi Bitmap ke ImageBitmap
                        contentDescription = null,
                        modifier = Modifier
                            .size(400.dp)
                            .padding(20.dp)
                    )
                }
            }
        }
    }
}

private fun saveImageToInternalStorage(context: Context, uri: Uri): Uri? {
    try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        inputStream?.use { input ->
            val imageName = "gambar_${System.currentTimeMillis()}.png"
            val imagePath = File(context.filesDir, "assets")
            imagePath.mkdirs()
            val outputFile = File(imagePath, imageName)
            val outputStream = FileOutputStream(outputFile)
            input.copyTo(outputStream)
            outputStream.close()

            // Mendapatkan URI menggunakan FileProvider
            return FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                outputFile
            )
        }
    } catch (e: Exception) {
        Log.e("SaveImageToAssets", "Error saving image: ${e.message}")
    }
    return null
}


@Composable
fun ImageButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.AddPhotoAlternate,
            contentDescription = stringResource(R.string.pilih_gambar),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
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
fun DetailScreenPreview() {
    Assessment1Theme {
        BukuDetailScreen(rememberNavController())
    }
}