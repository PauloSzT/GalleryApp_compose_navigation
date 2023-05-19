package com.example.galleryapp_compose.ui.galleryscreen

import com.example.galleryapp_compose.models.Photo
import kotlinx.coroutines.flow.StateFlow

data class GalleryScreenUiState (
    val photoList: StateFlow<List<Photo>>
)