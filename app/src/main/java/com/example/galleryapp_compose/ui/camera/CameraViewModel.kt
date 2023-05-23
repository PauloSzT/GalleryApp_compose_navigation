package com.example.galleryapp_compose.ui.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.app.ActivityCompat
import com.example.galleryapp_compose.ui.camera.CameraConstants.ERROR_MESSAGE
import com.example.galleryapp_compose.ui.camera.CameraConstants.IMAGE_FORMAT
import com.example.galleryapp_compose.ui.camera.CameraConstants.SUCCESS_MESSAGE
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor

class CameraViewModel (private val context: Context) {

    private val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    val cameraUiState = CameraUiState(
        ::takePhoto
    )

    private fun takePhoto(
        filenameFormat: String,
        imageCapture: ImageCapture,
        outputDirectory: File,
        executor: Executor,
        onImageCaptured: (Uri) -> Unit,
        onError: (ImageCaptureException) -> Unit
    ) {
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + IMAGE_FORMAT
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
            .setMetadata(ImageCapture.Metadata().apply {
                (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                )
                val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                this.location = lastLocation
            }).build()

        imageCapture.takePicture(outputOptions, executor, object: ImageCapture.OnImageSavedCallback {
            override fun onError(exception: ImageCaptureException) {
                Log.d("Error", ERROR_MESSAGE, exception)
                onError(exception)
            }

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                val msg = "$SUCCESS_MESSAGE $savedUri"
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                Log.d("This", msg)
                onImageCaptured(savedUri)

            }
        })
    }
}
