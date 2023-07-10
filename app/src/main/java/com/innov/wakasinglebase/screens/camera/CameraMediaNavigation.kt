package com.innov.wakasinglebase.screens.camera

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute


/**
 * Created by innov Victor on 4/2/2023.
 */
fun NavGraphBuilder.cameraMediaNavGraph(navController: NavController) {
    composable(route = DestinationRoute.CAMERA_ROUTE) {
        CameraMediaScreen(navController)
    }
}