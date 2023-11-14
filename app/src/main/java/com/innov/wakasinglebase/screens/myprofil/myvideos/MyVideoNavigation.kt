package com.innov.wakasinglebase.screens.myprofil.myvideos

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.myVideosNavGraph(navController: NavController) {
    composable(route = DestinationRoute.MY_VIDEO_ROUTE) {backStackEntry->
        val videoId =  backStackEntry.arguments?.getString("videoId")
        MyVideoScreen(navController,videoId)
    }
}