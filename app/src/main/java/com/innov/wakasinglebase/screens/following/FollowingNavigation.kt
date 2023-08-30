package com.innov.wakasinglebase.screens.following

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute.EXPLORE_ROUTE

/**
 * Created by innov  on 3/14/2023.
 */

fun NavGraphBuilder.followingNavGraph(navController: NavController) {
    composable(route = EXPLORE_ROUTE) {
        //println("Following ****************>>>>>>> ")
        FollowingScreen(navController)
    }
}