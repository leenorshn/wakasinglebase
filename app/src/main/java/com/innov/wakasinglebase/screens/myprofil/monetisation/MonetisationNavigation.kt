package com.innov.wakasinglebase.screens.myprofil.monetisation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.monetisationNavGraph(navController: NavController) {
    composable(route = DestinationRoute.MONETISATION_ROUTE) {
        MonetisationScreen(navController)
    }
}