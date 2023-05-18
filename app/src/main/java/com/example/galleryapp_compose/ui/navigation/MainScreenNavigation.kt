package com.example.galleryapp_compose.ui.navigation

import com.example.galleryapp_compose.R

sealed class BottomNavItem(var title: String, var icon: Int, var route: String) {
    object Detail : BottomNavItem("Detail", R.drawable.ic_detail, "detail")
    object Camera : BottomNavItem("Camera", R.drawable.ic_camera, "camera")
    object Gallery : BottomNavItem("Gallery", R.drawable.ic_gallery, "gallery")
}


