package com.innov.wakasinglebase.screens.home.video

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
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

fun NavGraphBuilder.videoNavGraph(navController: NavController) {
    composable(
         DestinationRoute.VIDEO_ROUTE,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern="https://wakamobile.vercel.app/{video}"
                    action=Intent.ACTION_VIEW
                }
            ),
        arguments = listOf(navArgument(
            name="video"
        ){
            type= NavType.StringType
            defaultValue="65703f1742916869b013e2b3"
        }),
         ) { backStackEntry ->
             val video = backStackEntry.arguments?.getString("video")
             VideoScreen(navController,video)
         }
}