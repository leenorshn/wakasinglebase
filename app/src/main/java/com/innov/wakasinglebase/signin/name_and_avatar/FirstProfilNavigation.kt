package com.innov.wakasinglebase.signin.name_and_avatar

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.firstProfileNavGraph(navController: NavController) {
    composable(route = DestinationRoute.FIRST_PROFILE) {
        FirstProfileScreen(navController)
    }
}