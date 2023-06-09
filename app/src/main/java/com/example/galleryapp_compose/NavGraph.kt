package com.example.galleryapp_compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.galleryapp_compose.NavGraphConstants.ARGUMENT_KEY
import com.example.galleryapp_compose.ui.camera.CameraScreen
import com.example.galleryapp_compose.ui.gallerydetail.GalleryDetailScreen
import com.example.galleryapp_compose.ui.galleryscreen.GalleryScreen
import com.example.galleryapp_compose.ui.navigation.BottomNavItem

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Gallery.route,
        modifier = modifier.fillMaxSize()
    ) {
        composable(route = BottomNavItem.Gallery.route) {
            GalleryScreen(navController)
        }
        composable(route = BottomNavItem.Camera.route) {
            CameraScreen()
        }
        composable(
            route = BottomNavItem.Detail.route,
            arguments = listOf(navArgument(ARGUMENT_KEY) { type = NavType.IntType })
        ) { backStackEntry ->
            val photoId = backStackEntry.arguments?.getInt(ARGUMENT_KEY) ?: 0
            GalleryDetailScreen(photoId = photoId)
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
                modifier = Modifier
                    .height(50.dp)
                    .align(alignment = CenterVertically),
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
                }
            )
        }
    }
}
