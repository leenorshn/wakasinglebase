package com.innov.wakasinglebase.screens.friends

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.friendsNavGraph(navController: NavController) {
    composable(route = DestinationRoute.FRIENDS_ROUTE) {
        FriendScreen(navController)
    }
}