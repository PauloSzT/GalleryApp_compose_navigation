package com.example.galleryapp_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp_compose.ui.navigation.BottomNavItem
import com.example.galleryapp_compose.ui.theme.GalleryAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            var displayBottomBar by remember { mutableStateOf(true) }
            DisposableEffect(key1 = Unit) {
                val destinationListener =
                    NavController.OnDestinationChangedListener { _ , destination, _ ->
                        displayBottomBar = destination.route != BottomNavItem.Detail.route
                    }
                navHostController.addOnDestinationChangedListener(destinationListener)
                onDispose { navHostController.removeOnDestinationChangedListener(destinationListener) }
            }
            GalleryAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (displayBottomBar) {
                            BottomNavigation(navController = navHostController)
                        }
                    }
                ) { innerPadding ->
                    NavigationGraph(
                        navController = navHostController,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}
