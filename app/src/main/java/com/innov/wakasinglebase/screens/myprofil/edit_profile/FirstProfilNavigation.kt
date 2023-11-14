package com.innov.wakasinglebase.screens.myprofil.edit_profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.firstProfileNavGraph(navController: NavController) {
    composable(route = DestinationRoute.EDIT_PROFILE) {
        FirstProfileScreen(navController)
    }
}