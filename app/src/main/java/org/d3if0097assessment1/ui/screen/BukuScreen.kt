package org.d3if0097assessment1.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Church
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import org.d3if0097assessment1.R
import org.d3if0097assessment1.database.BukuDb
import org.d3if0097assessment1.model.Buku
import org.d3if0097assessment1.model.GudangBuku
import org.d3if0097assessment1.navigation.Screen
import org.d3if0097assessment1.ui.theme.Assessment1Theme
import org.d3if0097assessment1.util.ViewModelFactory

@Composable
fun BukuScreen(
    navHostController: NavHostController,
) {
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            SootheBottomNavigation(navHostController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(Screen.FormBaru.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.tambahan_buku),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { contentPadding ->
        SearchFieldComposeTheme(
            navHostController,
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@Composable
fun SearchFieldComposeTheme(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val content = LocalContext.current
    val db = BukuDb.getInstance(content)
    val factory = ViewModelFactory(db.dao)
    val viewModel: GudangBuku = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (data.isEmpty()) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.list_kosong))
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(data) {
                    BukuItem(buku = it) {
                        navHostController.navigate(Screen.FormUbah.withId(it.id))
                    }
                    Divider()
                }
            }
        }
    }
}

@Composable
private fun SootheBottomNavigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Church,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.utama))
            },
            selected = false,
            onClick = {
                navHostController.navigate(Screen.Buku.route)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.buku))
            },
            selected = true,
            onClick = {
                navHostController.navigate(Screen.Buku.route)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Newspaper,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.berita_acara))
            },
            selected = false,
            onClick = {
                navHostController.navigate(Screen.Buku.route)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.data_diri))
            },
            selected = false,
            onClick = {
                navHostController.navigate(Screen.About.route)
            }
        )
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TopAppBar(navHostController: NavHostController) {
//    TopAppBar(
//        title = { Text(text = stringResource(id = R.string.app_name)) },
//        colors = TopAppBarDefaults.mediumTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            titleContentColor = MaterialTheme.colorScheme.primary
//        ),
//        actions = {
//            IconButton(
//                onClick = {
//                    navHostController.popBackStack(
//                        navHostController.graph.startDestinationId,
//                        false
//                    )
//                }
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Info,
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.primary,
//                )
//            }
//        }
//    )
//}

@Composable
fun BukuItem(
    buku: Buku,
    onClick: () -> Unit
) {
    val painter = rememberImagePainter(buku.gambar)

    Card(
        modifier = Modifier
            .width(175.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                Image(
                    painter = painter,
                    contentDescription = buku.judul,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.small)
                )
                Column(
                    modifier = Modifier
                        .padding(bottom = 16.dp, start = 16.dp)
                        .width(245.dp)
                        .clickable { onClick() }
                ) {
                    Text(
                        text = buku.judul,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = buku.penulis,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assessment1Theme {
        BukuScreen(rememberNavController())
    }
}