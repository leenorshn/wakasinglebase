package com.innov.wakasinglebase.screens.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.AuthState
import com.innov.wakasinglebase.core.DestinationRoute.HOME_SCREEN_ROUTE

/**
 * Created by innov  on 3/14/2023.
 */

fun NavGraphBuilder.homeNavGraph(navController: NavController, authState: AuthState) {
    composable(route = HOME_SCREEN_ROUTE) {
        HomeScreen(navController,authState)
    }
}