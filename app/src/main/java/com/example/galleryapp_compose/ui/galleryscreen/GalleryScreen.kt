package com.example.galleryapp_compose.ui.galleryscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.galleryapp_compose.ui.navigation.BottomNavItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryScreen(
    navController: NavController
) {
    val viewModel = GalleryScreenViewModel(LocalContext.current)
    val photoList by viewModel.galleryScreenUiState.photoList.collectAsState()
    val photoListState = rememberPagerState {
        Int.MAX_VALUE
    }
    if (photoList.isNotEmpty()) {
        VerticalPager(
            state = photoListState,
            pageSpacing = 8.dp,
            pageContent = {
                val index = it.mod(photoList.size)
                AsyncImage(
                    model = photoList[index].pathName,
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            navController.navigate(BottomNavItem.Detail.routeWithArgs(photoList[index].id))
                        }
                )
            }
        )
    } else {
        Text(text = "No Photos on Gallery")
    }
}
