package com.innov.wakasinglebase.screens.camera


import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.core.extension.getCurrentBrightness
import com.innov.wakasinglebase.screens.camera.tabs.CameraScreen


@RequiresApi(Build.VERSION_CODES.P)


@Composable
fun CameraMediaScreen(
    navController: NavController,
    cameraMediaViewModel: CameraMediaViewModel = hiltViewModel()
) {


    val tabs = Tabs.values().asList()
    val context = LocalContext.current
    val minimumScreenBrightness = 0.25f

    DisposableEffect(key1 = Unit) {
        val attrs = (context as Activity).window.attributes.apply {
            if (context.getCurrentBrightness() < minimumScreenBrightness) {
                screenBrightness = minimumScreenBrightness
            }
        }
        context.window.attributes = attrs
        onDispose {
            context.window.attributes = attrs.apply {
                screenBrightness = context.getCurrentBrightness()
            }
        }
    }


    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            Box(modifier = Modifier.weight(1f)) {
                 CameraScreen(
                            navController = navController,
                            viewModel = cameraMediaViewModel,
                            cameraOpenType = tabs[0]
                        )

                    }
                }
            }

}








