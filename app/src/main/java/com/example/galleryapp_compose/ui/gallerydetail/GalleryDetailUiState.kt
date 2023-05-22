package com.example.galleryapp_compose.ui.gallerydetail

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.StateFlow

data class GalleryDetailUiState(
    val currentPhotoUrl: StateFlow<String?>,
    val currentPhotoLatLong: StateFlow<LatLng?>
)
