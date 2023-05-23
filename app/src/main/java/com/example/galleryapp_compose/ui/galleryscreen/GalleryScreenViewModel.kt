package com.example.galleryapp_compose.ui.galleryscreen

import android.content.Context
import android.os.Environment
import com.example.galleryapp_compose.models.Photo
import com.example.galleryapp_compose.ui.galleryscreen.GalleryScreenConstants.CHILD_ROUTE
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File

class GalleryScreenViewModel(context: Context) {
    private val photoList = MutableStateFlow<List<Photo>>(emptyList())
    private val folder: File = Environment.getExternalStorageDirectory()

    init {
        val imagesList = File(folder, CHILD_ROUTE)
            .listFiles()?.mapIndexed { index, item ->
                Photo(pathName = item.path, id = index)
            }
        if (imagesList != null) {
            photoList.value = imagesList
        }
    }

    val galleryScreenUiState = GalleryScreenUiState(
        photoList = photoList
    )
}
