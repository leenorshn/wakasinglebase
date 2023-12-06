package com.innov.wakasinglebase.screens.home.video_detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

/**
 *
 *
 * composable(
 * "details?article={argument}",
 * deepLinks = listOf(navDeepLink {
 *     uriPattern = "https://composables.com/blog/{argument}"
 * }),
 * ) { backStackEntry ->
 *     val article = backStackEntry.arguments?.getString("argument")
 *     Text("Showing /$article")
 * }
 * */

fun NavGraphBuilder.videoDetailNavGraph(navController: NavController) {
    composable(
         DestinationRoute.VIDEO_DETAIL_ROUTE,

         ) { backStackEntry ->
             val video = backStackEntry.arguments?.getString("video")
             VideoDetailScreen(navController,video)
         }
}