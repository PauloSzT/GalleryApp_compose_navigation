package com.example.galleryapp_compose.ui.galleryscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.galleryapp_compose.R
import com.example.galleryapp_compose.models.Photo
import com.example.galleryapp_compose.ui.galleryscreen.GalleryScreenConstants.GALLERY_EMPTY_MESSAGE
import com.example.galleryapp_compose.ui.galleryscreen.GalleryScreenConstants.PREVIEW_IMAGE_URL
import com.example.galleryapp_compose.ui.navigation.BottomNavItem
import com.example.galleryapp_compose.ui.theme.GalleryAppTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun GalleryScreen(
    navController: NavController
) {
    val viewModel = GalleryScreenViewModel(LocalContext.current)

    GalleryScreenContent(
        galleryScreenUiState = viewModel.galleryScreenUiState,
        onNavigateDetails = {
            navController.navigate(BottomNavItem.Detail.routeWithArgs(it))
        })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryScreenContent(
    galleryScreenUiState: GalleryScreenUiState,
    onNavigateDetails: (Int) -> Unit
) {
    val photoList by galleryScreenUiState.photoList.collectAsState()
    val photoListState = rememberPagerState {
        Int.MAX_VALUE
    }
    if (photoList.isNotEmpty()) {
        VerticalPager(
            modifier = Modifier.fillMaxSize(),
            state = photoListState,
            contentPadding = PaddingValues(bottom = 100.dp),
            pageSpacing = 8.dp,
            pageContent = {
                val index = it.mod(photoList.size)
                if (LocalInspectionMode.current) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray)
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.preview),
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight
                        )
                    }
                } else {
                    AsyncImage(
                        model = photoList[index].pathName,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                onNavigateDetails(photoList[index].id)
                            }
                    )
                }
            }
        )
    } else {
        Text(text = GALLERY_EMPTY_MESSAGE)
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryScreenContentPreview() {
    GalleryAppTheme {
        val photoList = List(5) {
            Photo(
                pathName = PREVIEW_IMAGE_URL,
                id = it + 1
            )
        }
        val galleryScreenUiState = GalleryScreenUiState(photoList = MutableStateFlow(photoList))
        GalleryScreenContent(galleryScreenUiState, onNavigateDetails = {})
    }
}
