package com.example.galleryapp_compose.ui.galleryscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.galleryapp_compose.ui.navigation.BottomNavItem


@Composable
fun GalleryScreen(
    navController: NavController
) {
    val viewModel = GalleryScreenViewModel(LocalContext.current)
    val photoList by viewModel.galleryScreenUiState.photoList.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        content = {
            if(photoList.isNotEmpty()){
                photoList.forEach { photo ->
                    item {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.Black)
                                .clickable { navController.navigate(BottomNavItem.Detail.route) },
                            contentAlignment = Alignment.Center,
                        ) {
                            AsyncImage(
                                model = photo.pathName,
                                contentDescription = photo.id.toString()
                            )
                        }
                    }
                }
            }else{
                item { Text(text = "No Photos on Gallery") }
            }

        }
    )
}
