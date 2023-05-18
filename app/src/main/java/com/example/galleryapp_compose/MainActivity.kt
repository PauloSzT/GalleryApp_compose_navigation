package com.example.galleryapp_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp_compose.ui.theme.GalleryAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            GalleryAppTheme {
              Scaffold (
                  bottomBar = {
                      BottomNavigation(navController = navHostController)
                  }
              ){
                    NavigationGraph(
                        navController = navHostController,
                        modifier = Modifier.padding(it))
              }
            }
        }
    }
}
