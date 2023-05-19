package com.example.galleryapp_compose.models

data class PhotoCollection(
    var areItemsBeingSelected: Boolean = false,
    val items: List<Photo>
)
