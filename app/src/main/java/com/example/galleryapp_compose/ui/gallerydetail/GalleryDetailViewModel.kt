package com.example.galleryapp_compose.ui.gallerydetail

import android.os.Environment
import androidx.exifinterface.media.ExifInterface
import com.example.galleryapp_compose.ui.gallerydetail.GalleryDetailConstants.CHILD_ROUTE
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File

class GalleryDetailViewModel(private val photoId: Int) {

    private val currentPhotoUrl = MutableStateFlow<String?>(null)
    private val currentPhotoLatLong = MutableStateFlow<LatLng?>(null)
    private val folder = Environment.getExternalStorageDirectory()

    val galleryDetailUiState = GalleryDetailUiState(
        currentPhotoUrl = currentPhotoUrl,
        currentPhotoLatLong = currentPhotoLatLong
    )

    init {
        currentPhotoLatLong.value = getGPSCoordinates()
    }

    private fun getGPSCoordinates(): LatLng {
        var returnedLatLng = LatLng(0.0, 0.0)
        val file = File(folder, CHILD_ROUTE)
        file.listFiles()?.forEachIndexed { index, image ->
            if (index == photoId) {
                currentPhotoUrl.value = image.path
                val exifInterface = ExifInterface(image.path)
                val latLong = exifInterface.latLong
                if (latLong != null) {
                    val latitude = latLong[0]
                    val longitude = latLong[1]
                    returnedLatLng = LatLng(latitude, longitude)
                }
            }
        }
        return returnedLatLng
    }
}
