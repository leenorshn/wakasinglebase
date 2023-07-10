package com.innov.wakasinglebase.screens.explore

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute.EXPLORE_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.HOME_SCREEN_ROUTE

/**
 * Created by innov  on 3/14/2023.
 */

fun NavGraphBuilder.followingNavGraph(navController: NavController) {
    composable(route = EXPLORE_ROUTE) {
        //println("Following ****************>>>>>>> ")
        FollowingScreen(navController)
    }
}