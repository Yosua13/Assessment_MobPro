package org.d3if0097assessment1.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0097assessment1.R
import org.d3if0097assessment1.model.GudangBuku
import org.d3if0097assessment1.navigation.Screen
import org.d3if0097assessment1.ui.theme.Assessment1Theme

@Composable
fun AboutScreen(
    navHostController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopAppBarAbout(navHostController)
        },
        bottomBar = {
            SootheBottomNavigation(navHostController)
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo_gereja),
                contentDescription = null,
                modifier = Modifier
                    .padding(padding)
            )
            Text(
                text = stringResource(R.string.tentang_kami),
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp),
                textAlign = TextAlign.Justify
            )
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
            selected = false,
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
            selected = true,
            onClick = {
                navHostController.navigate(Screen.About.route)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarAbout(navHostController: NavHostController) {
    androidx.compose.material3.TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {
                    navHostController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        title = { Text(text = stringResource(id = R.string.about)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AboutPreview() {
    Assessment1Theme {
        AboutScreen(rememberNavController())
    }
}