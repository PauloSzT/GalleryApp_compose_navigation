package com.example.galleryapp_compose.models

data class Photo (
    val pathName: String,
    var isSelected: Boolean = false,
    val id: Int
)
