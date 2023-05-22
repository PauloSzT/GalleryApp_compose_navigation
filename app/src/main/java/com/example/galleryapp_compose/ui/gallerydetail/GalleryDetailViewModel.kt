package com.example.galleryapp_compose.ui.gallerydetail

import android.media.ExifInterface
import android.os.Environment
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File

class GalleryDetailViewModel(val photoId: Int) {

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
            file.listFiles()?.forEachIndexed { index, file ->
                if (index == photoId) {
                    currentPhotoUrl.value = file.path
                    val exifInterface = ExifInterface(file.path)
                    val latLong = exifInterface.getLatLong()
                    if (latLong != null) {
                        val latitude = latLong[0].toDouble()
                        val longitude = latLong[1].toDouble()
                        returnedLatLng = LatLng(latitude, longitude)
                    }
                }
            }
        return returnedLatLng
    }

    private fun ExifInterface.getLatLong(): FloatArray? {
        val latLong = FloatArray(2)
        return if (getLatLong(latLong)) {
            latLong
        } else {
            null
        }
    }

    companion object {
        private const val CHILD_ROUTE = "/Pictures/Gallery_App"
    }
}
