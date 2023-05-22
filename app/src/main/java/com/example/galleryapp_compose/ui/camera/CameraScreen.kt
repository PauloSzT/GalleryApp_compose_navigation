package com.example.galleryapp_compose.ui.camera

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen() {

    val viewModel = CameraViewModel(LocalContext.current)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val multiplePermissionState = rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA
            )
        )
        PermissionsRequired(
            multiplePermissionsState = multiplePermissionState,
            permissionsNotGrantedContent = { /* ... */ },
            permissionsNotAvailableContent = { /* ... */ }
        ) {
            CameraConfig(
                viewModel.cameraUiState.takePhoto
            )
        }
        LaunchedEffect(key1 = Unit){
            if(!multiplePermissionState.allPermissionsGranted){
                multiplePermissionState.launchMultiplePermissionRequest()
            }
        }
    }
}
