package com.innov.wakasinglebase.screens.community

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.communityNavGraph(navController: NavController) {
    composable(route = DestinationRoute.THREADS_ROUTE) {
        CommunityScreen(navController)
    }
}