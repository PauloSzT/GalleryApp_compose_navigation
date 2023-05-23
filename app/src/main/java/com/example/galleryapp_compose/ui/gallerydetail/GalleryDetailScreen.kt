package com.example.galleryapp_compose.ui.gallerydetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.galleryapp_compose.R
import com.example.galleryapp_compose.ui.gallerydetail.GalleryDetailConstants.PREVIEW_IMAGE_URL
import com.example.galleryapp_compose.ui.theme.GalleryAppTheme
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun GalleryDetailScreen(
    photoId: Int
) {
    val viewModel = GalleryDetailViewModel(photoId)

    GalleryDetailContent(
        galleryDetailUiState = viewModel.galleryDetailUiState
    )
}

@Composable
fun GalleryDetailContent(
    galleryDetailUiState: GalleryDetailUiState
) {
    val currentPhotoUrl by galleryDetailUiState.currentPhotoUrl.collectAsState()
    val currentPhotoLatLong by galleryDetailUiState.currentPhotoLatLong.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        if (LocalInspectionMode.current) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.preview),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )
            }
        } else {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight,
                model = currentPhotoUrl,
                contentDescription = null
            )
        }
    }
    GoogleMapBottomSheet(currentPhotoLatLong)
}

@Preview()
@Composable
fun GalleryDetailContentPreview() {
    GalleryAppTheme {
        val currentPhotoUrl = PREVIEW_IMAGE_URL
        val currentPhotoLatLong = LatLng(16.000, 76.000)
        val galleryDetailUiState = GalleryDetailUiState(
            currentPhotoUrl = MutableStateFlow(currentPhotoUrl),
            currentPhotoLatLong = MutableStateFlow(currentPhotoLatLong)
        )
        GalleryDetailContent(galleryDetailUiState)
    }
}
