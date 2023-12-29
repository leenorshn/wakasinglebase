package com.innov.wakasinglebase.screens.camera


import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.AuthState
import com.innov.wakasinglebase.common.UnAuthorizedInboxScreen
import com.innov.wakasinglebase.core.DestinationRoute.LOGIN_OR_SIGNUP_WITH_PHONE_ROUTE
import com.innov.wakasinglebase.core.extension.getCurrentBrightness
import com.innov.wakasinglebase.screens.camera.tabs.CameraScreen


@RequiresApi(Build.VERSION_CODES.P)


@Composable
fun CameraMediaScreen(
    navController: NavController,
    authState:AuthState,
    viewModel: CameraMediaViewModel = hiltViewModel()
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

if (authState.success){
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            Box(modifier = Modifier.weight(1f)) {
                CameraScreen(
                    navController = navController,
                    // viewModel = cameraMediaViewModel,
                    cameraOpenType = tabs[0]
                )

            }
        }
    }
}else{
    UnAuthorizedInboxScreen {
        navController.navigate(LOGIN_OR_SIGNUP_WITH_PHONE_ROUTE)
    }
}


}








