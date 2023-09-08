package com.example.testapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.testapplication.databinding.FragmentMainContainerBinding
import com.example.testapplication.databinding.FragmentMainSwipeContentBinding
import com.example.testapplication.ui.theme.TestApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestApplicationTheme {
                MainScreen()
            }
        }
    }

    @Composable
    private fun MainScreen(
        drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    ) {
        ModalNavigationDrawer(
            drawerContent = { DrawerContent() },
            drawerState = drawerState,
        ) {
            Scaffold(
                topBar = { TopBar(drawerState) }
            ) { paddingValues ->
                val modifier = Modifier.padding(
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr) + 16.dp,
                    top = paddingValues.calculateTopPadding() + 8.dp,
                    end = paddingValues.calculateEndPadding(LayoutDirection.Ltr) + 16.dp,
                    bottom = paddingValues.calculateBottomPadding() + 8.dp
                )
                FragmentContentWithColumn(modifier) // Fragment is displayed and no swipe up bug
//                FragmentContentWithoutColumn(modifier) // Fragment is displayed and swipe up bug
//                FragmentContentWithColumnSwipeRefresh(modifier) // Fragment has 0 height
//                GreetingWithColumn(modifier) // No swipe up bug
//                GreetingWithoutColumn(modifier) // Swipe up bug
            }
        }
    }

    @Composable
    private fun FragmentContentWithColumn(modifier: Modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            AndroidViewBinding(
                factory = FragmentMainContainerBinding::inflate,
                modifier = modifier.fillMaxSize()
            )
        }
    }

    @Composable
    private fun FragmentContentWithoutColumn(modifier: Modifier) {
        AndroidViewBinding(
            factory = FragmentMainContainerBinding::inflate,
            modifier = modifier.fillMaxSize()
        )
    }

    @Composable
    private fun FragmentContentWithColumnSwipeRefresh(modifier: Modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            AndroidViewBinding(
                factory = FragmentMainSwipeContentBinding::inflate,
                modifier = modifier.fillMaxSize()
            )
        }
    }

    @Composable
    fun GreetingWithColumn(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = "Greetings!",
                modifier = Modifier
            )
        }
    }

    @Composable
    fun GreetingWithoutColumn(modifier: Modifier = Modifier) {
        Text(
            text = "Greetings!",
            modifier = modifier
        )
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun TopBar(drawerState: DrawerState) {
        val coroutineScope = rememberCoroutineScope()
        TopAppBar(
            title = {}, // no title
            navigationIcon = {
                IconButton(
                    onClick = { coroutineScope.launch { drawerState.open() } }
                ) {
                    Icon(
                        Icons.Rounded.Menu,
                        contentDescription = "MenuButton"
                    )
                }
            },
        )
    }
}

@Composable
fun DrawerContent(modifier: Modifier = Modifier) {
    ModalDrawerSheet(modifier.width(260.dp)) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(text = "First element", modifier = Modifier.padding(8.dp))
                Text(text = "Second element", modifier = Modifier.padding(8.dp))
            }
        }
    }
}
