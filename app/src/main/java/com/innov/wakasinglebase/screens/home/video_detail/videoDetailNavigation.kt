package com.innov.wakasinglebase.screens.home.video_detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink

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
         "details?video={video}",
         deepLinks = listOf(navDeepLink {
                 uriPattern = "https://d2y4y6koqmb0v7.cloudfront.net/{video}"
             }),
         ) { backStackEntry ->
             val video = backStackEntry.arguments?.getString("video")
             VideoDetailScreen(navController)
         }
}