package org.d3if0097assessment1.ui.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0097assessment1.R
import org.d3if0097assessment1.model.Buku
import org.d3if0097assessment1.model.GudangBuku
import org.d3if0097assessment1.navigation.Screen
import org.d3if0097assessment1.ui.theme.Assessment1Theme

@Composable
fun MainScreen(
    navHostController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopAppBar(navHostController)
        },
        bottomBar = {
            SootheBottomNavigation(navHostController)
        }
    ) { contentPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = contentPadding,
        ) {
            items(GudangBuku.dataBuku) { buku ->
                BukuScreen(
                    buku = buku,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun SootheBottomNavigation(navHostController: NavHostController, modifier: Modifier = Modifier) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.utama))
            },
            selected = true,
            onClick = {
                navHostController.navigate(Screen.Home.route)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.about))
            },
            selected = false,
            onClick = {
                navHostController.navigate(Screen.About.route)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navHostController: NavHostController) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            IconButton(
                onClick = {
                    navHostController.popBackStack(navHostController.graph.startDestinationId, false)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    )
}

@Composable
fun BukuScreen(
    buku: Buku,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .width(175.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                GambarBuku(gambarBuku = buku.gambar, kontentBuku = buku.judul)
                InformasiBuku(judulBuku = buku.judul, namaPenulis = buku.penulis)
            }
        }
    }
}

@Composable
fun InformasiBuku(
    @StringRes judulBuku: Int,
    @StringRes namaPenulis: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(bottom = 16.dp, start = 16.dp)
            .width(245.dp)
    ) {
        Text(
            text = stringResource(judulBuku),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(namaPenulis),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun GambarBuku(
    @DrawableRes gambarBuku: Int,
    @StringRes kontentBuku: Int,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(gambarBuku),
        contentDescription = stringResource(kontentBuku),
        contentScale = ContentScale.FillWidth,
        modifier = modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.small)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assessment1Theme {
        MainScreen(rememberNavController())
    }
}