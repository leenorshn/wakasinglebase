package com.innov.wakasinglebase.screens.threads.follower

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.followerNavGraph(navController: NavController) {
    composable(route = DestinationRoute.FOLLOWER_ROUTE) {
        FollowerScreen(navController)
    }
}