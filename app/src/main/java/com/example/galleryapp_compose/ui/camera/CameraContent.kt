package com.example.galleryapp_compose.ui.camera

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import java.io.File
import java.util.concurrent.Executor

@Composable
fun CameraContent(
    context: Context,
    previewView: PreviewView,
    imageCapture: ImageCapture,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    takePhoto: (
        String,
        ImageCapture,
        File,
        Executor,
        (Uri) -> Unit,
        (ImageCaptureException) -> Unit
    ) -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ){
        AndroidView( {previewView}, modifier = Modifier.fillMaxSize())

        IconButton(
            modifier = Modifier.padding(bottom = 20.dp),
            onClick = {
                val mediaDir = File(Environment.getExternalStorageDirectory(), "/Pictures/Gallery_App")
                if (!mediaDir.exists()) {
                    mediaDir.mkdirs()
                }
                takePhoto(
                    "yyyy-MM-dd-HH-mm-ss-SSS",
                    imageCapture,
                    mediaDir,
                    context.mainExecutor,
                    onImageCaptured,
                    onError
                )
            },
            content = {
                Icon(
                    imageVector = Icons.Sharp.Done,
                    contentDescription = "Take picture",
                    tint = Color.White,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(1.dp)
                        .border(1.dp, Color.White, CircleShape)
                )
            }
        )
    }
}
