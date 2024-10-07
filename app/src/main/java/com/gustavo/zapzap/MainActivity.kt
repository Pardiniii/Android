package com.gustavo.zapzap

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CircleNotifications
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gustavo.zapzap.ui.theme.ZapZapTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZapZapTheme {
                App()
            }
        }
    }
}

class BottomAppBarItem(
    val icon: ImageVector,
    val label: String
)

class TopAppBarItem(
    val title: String,
    val icons: List<ImageVector> = emptyList()
)

sealed class ScreenItem(
    val topAppItem: TopAppBarItem,
    val bottomAppItem: BottomAppBarItem
) {
    data object Chats : ScreenItem(

        topAppItem = TopAppBarItem(
            title = "Zap Zap",
            icons = listOf(
                Icons.Default.CameraAlt,
                Icons.Default.MoreVert
            )
        ),
        bottomAppItem = BottomAppBarItem(
            icon = Icons.AutoMirrored.Filled.Message,
            label = "Chats"
        )

    )

    data object Atualizacoes : ScreenItem(
        topAppItem = TopAppBarItem(
            title = "Atualizações",
            icons = listOf(
                Icons.Default.CameraAlt,
                Icons.Default.Search,
                Icons.Default.MoreVert
            )
        ),
        bottomAppItem = BottomAppBarItem(
            icon = Icons.Default.CircleNotifications,
            label = "Atualizações"
        )
    )

    data object Comunidades : ScreenItem(
        topAppItem = TopAppBarItem(
            title = "Comunidades",
            icons = listOf(
                Icons.Default.CameraAlt,
                Icons.Default.MoreVert
            )
        ),
        bottomAppItem = BottomAppBarItem(
            icon = Icons.Default.People,
            label = "Comunidades"
        )
    )

    data object Chamadas : ScreenItem(
        topAppItem = TopAppBarItem(
            title = "Chamadas",
            icons = listOf(
                Icons.Default.CameraAlt,
                Icons.Default.Search,
                Icons.Default.MoreVert
            )
        ),
        bottomAppItem = BottomAppBarItem(
            icon = Icons.Default.Phone,
            label = "Chamadas"
        )
    )
}


@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun App() {
    val screens = remember {
        listOf(
            ScreenItem.Chats,
            ScreenItem.Atualizacoes,
            ScreenItem.Comunidades,
            ScreenItem.Chamadas
        )
    }

    var currentScreen by remember {
        mutableStateOf(screens.first())
    }
    val pagerState = rememberPagerState {
        screens.size
    }

    LaunchedEffect(currentScreen) {
        pagerState.animateScrollToPage(screens.indexOf(currentScreen))
    }

    LaunchedEffect(pagerState.targetPage) {
        currentScreen = screens[pagerState.targetPage]
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(currentScreen.topAppItem.title)
                },
                //colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xff234567), titleContentColor = Color.White),
                actions = {
                    Row(
                        Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        currentScreen.topAppItem.icons.forEach{icon ->
                            Icon(icon, contentDescription = null)
                        }
                    }
                }
            )
        }, bottomBar = {
            BottomAppBar() {
                screens.forEach { screen ->
                    with(screen.bottomAppItem){
                        NavigationBarItem(
                            selected = screen == currentScreen,
                            onClick = {
                                currentScreen = screen
                            },
                            icon = {
                                Icon(icon, contentDescription = null)
                            },
                            label = {
                                Text(label)
                            }
                        )
                    }

                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            pagerState,
            Modifier.padding(innerPadding)
        ) { page ->
            val item = screens[page]
            when (item) {
                ScreenItem.Chats -> ChatListScreen()
                ScreenItem.Chamadas -> ChamadasScreen()
                ScreenItem.Comunidades -> ComunidadesScreen()
                ScreenItem.Atualizacoes -> AtualizacoesScreen()
            }
        }
    }
}


@Composable
fun ChatListScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Text(
            "Lista de chats",
            modifier.align(Alignment.Center),
            style = TextStyle.Default.copy(
                fontSize = 32.sp
            )
        )
    }
}

@Composable
fun AtualizacoesScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Text(
            "Atualizações de Status",
            modifier.align(Alignment.Center),
            style = TextStyle.Default.copy(
                fontSize = 32.sp
            )
        )
    }
}

@Composable
fun ComunidadesScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Text(
            "Lista de comunidades",
            modifier.align(Alignment.Center),
            style = TextStyle.Default.copy(
                fontSize = 32.sp
            )
        )
    }
}

@Composable
fun ChamadasScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Text(
            "Lista de chamadas",
            modifier.align(Alignment.Center),
            style = TextStyle.Default.copy(
                fontSize = 32.sp
            )
        )
    }
}

@Preview
@Composable
private fun AppPreview() {
    ZapZapTheme {
        App()
    }
}
