package com.innov.wakasinglebase.screens.threads

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.friendsNavGraph(navController: NavController) {
    composable(route = DestinationRoute.THREADS_ROUTE) {
        ThreadsScreen(navController)
    }
}