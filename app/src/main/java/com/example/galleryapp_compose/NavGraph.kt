package com.example.galleryapp_compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp_compose.ui.gallerydetail.GalleryDetailScreen
import com.example.galleryapp_compose.ui.navigation.BottomNavItem

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Gallery.route,
        modifier = modifier.fillMaxSize()
    ) {
        composable(route = BottomNavItem.Gallery.route) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
                    .clickable { navController.navigate(BottomNavItem.Detail.route) }
            )
        }
        composable(route = BottomNavItem.Camera.route) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            )
        }
        composable(route = BottomNavItem.Detail.route) {
            GalleryDetailScreen()
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(BottomNavItem.Gallery, BottomNavItem.Camera)
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                },
            )
        }
    }
}
