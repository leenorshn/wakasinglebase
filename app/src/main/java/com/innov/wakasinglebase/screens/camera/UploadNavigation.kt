package com.innov.wakasinglebase.screens.camera



import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute


/**
 * Created by innov Victor on 4/2/2023.
 */
fun NavGraphBuilder.uploadNavGraph(navController: NavController) {
    composable(route = DestinationRoute.UPLOAD_ROUTE,

    ) {

        val result=navController.previousBackStackEntry?.savedStateHandle?.get<UploadData>(
            key = "uploadData"
        )
        UploadScreen(navController,uri= result?.uri!!, fileName =result.fileName)
    }
}