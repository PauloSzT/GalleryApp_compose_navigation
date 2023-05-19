package com.example.galleryapp_compose.ui.galleryscreen

import android.content.Context
import android.os.Environment
import com.example.galleryapp_compose.models.Photo
import com.example.galleryapp_compose.models.PhotoCollection
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File

class GalleryScreenViewModel(private val context: Context) {
    private val photoList = MutableStateFlow<List<Photo>>(emptyList())
    private val folder = Environment.getExternalStorageDirectory()

    init {
        val imagesList = File(folder, CHILD_ROUTE)
            .listFiles()?.mapIndexed { index, item ->
                Photo(pathName = item.path, id = index)
            }
        var imagesCollection = PhotoCollection(false, imagesList ?: emptyList())
        if (imagesList != null) {
            photoList.value = imagesList
        }
    }

    val galleryScreenUiState = GalleryScreenUiState (
        photoList = photoList
    )

    companion object {
        private const val CHILD_ROUTE = "/Pictures/Gallery_App"
    }
}
