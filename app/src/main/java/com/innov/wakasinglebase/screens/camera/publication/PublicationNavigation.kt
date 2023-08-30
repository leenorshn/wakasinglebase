package com.innov.wakasinglebase.screens.camera.publication

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

@RequiresApi(Build.VERSION_CODES.P)
fun NavGraphBuilder.publicationNavGraph(navController: NavController) {
//    val viewModel=SignInViewModel()
//    val state=viewModel.checkUser()
////   if(){
    composable(route = DestinationRoute.PUBLICATION_SCREEN_ROUTE) {
            backStackEntry->
        val uri =  backStackEntry.arguments?.getString("uri")

        PublicationScreen(navController, uri = Uri.parse(uri))
    }
}



