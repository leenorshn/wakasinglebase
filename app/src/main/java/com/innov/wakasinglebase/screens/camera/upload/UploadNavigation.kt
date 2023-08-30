package com.innov.wakasinglebase.screens.camera.upload



import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute


/**
 * Created by innov Victor on 4/2/2023.
 */
fun NavGraphBuilder.uploadNavGraph(navController: NavController) {
    composable(route = DestinationRoute.UPLOAD_ROUTE,
    ) {backStackEntry->
        val uri =  backStackEntry.arguments?.getString("uri")
//        val moshi = Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
//        val jsonAdapter = moshi.adapter(UploadData::class.java).lenient()
//        val userObject = jsonAdapter.fromJson(userJson)

        UploadScreen(navController, uri = uri)
    }
}