package com.example.galleryapp_compose.ui.camera

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import java.io.File
import java.util.concurrent.Executor

data class CameraUiState(
    val takePhoto: (
        String,
        ImageCapture,
        File,
        Executor,
        (Uri) -> Unit,
        (ImageCaptureException) -> Unit
    ) -> Unit
)
