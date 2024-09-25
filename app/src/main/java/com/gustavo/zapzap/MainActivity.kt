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
    @OptIn(ExperimentalMaterial3Api::class)
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

sealed class NavItem(
    val icon: ImageVector,
    val label: String
) {
    data object Chats: NavItem(
        icon = Icons.AutoMirrored.Filled.Message,
        label = "Chats"
    )
    data object Atualizacoes: NavItem(
        icon = Icons.Default.CircleNotifications,
        label = "Atualizações"
    )
    data object Comunidades: NavItem(
        icon = Icons.Default.People,
        label = "Comunidades"
    )
    data object Chamadas: NavItem(
        icon = Icons.Default.Phone,
        label = "Chamadas"
    )
}


@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun App() {
    val listaIconsBottomBar = remember{
        listOf(
            NavItem.Chats,
            NavItem.Atualizacoes,
            NavItem.Comunidades,
            NavItem.Chamadas
        )
    }

    var selectedItem by remember {
        mutableStateOf(listaIconsBottomBar.first())
    }
    val pagerState = rememberPagerState {
        listaIconsBottomBar.size
    }

    LaunchedEffect(selectedItem) {
        pagerState.animateScrollToPage(listaIconsBottomBar.indexOf(selectedItem))
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedItem = listaIconsBottomBar[pagerState.currentPage]
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("ZapZap")
                },
                //colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xff234567), titleContentColor = Color.White),
                    actions = {
                    Row(
                        Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null)
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }
                }
            )
        }, bottomBar = {
            BottomAppBar() {
                listaIconsBottomBar.forEach { NavItem ->
                    NavigationBarItem(
                        selected = NavItem == selectedItem,
                        onClick = {
                            selectedItem = NavItem
                        },
                        icon = {
                            Icon(NavItem.icon, contentDescription = null)
                        },
                        label = {
                            Text(NavItem.label)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            pagerState,
            Modifier.padding(innerPadding)
        ) { page ->
            val item = listaIconsBottomBar[page]
            when(item) {
                NavItem.Chats -> ChatListScreen()
                NavItem.Chamadas -> ChamadasScreen()
                NavItem.Comunidades -> ComunidadesScreen()
                NavItem.Atualizacoes -> AtualizacoesScreen()
            }
        }
    }
}


@Composable
fun ChatListScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()){
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
    Box(modifier.fillMaxSize()){
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
    Box(modifier.fillMaxSize()){
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
    Box(modifier.fillMaxSize()){
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
