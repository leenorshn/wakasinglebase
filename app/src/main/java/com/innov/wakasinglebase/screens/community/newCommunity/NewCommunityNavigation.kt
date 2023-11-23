package com.innov.wakasinglebase.screens.community.newCommunity

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.newCommunityNavGraph(navController: NavController) {
    composable(route = DestinationRoute.NEW_COMMUNITY_ROUTE) {
        NewCommunityScreen(navController)
    }
}