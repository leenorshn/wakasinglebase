package com.innov.wakasinglebase.screens.myprofil.mybusiness

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.myBusinessNavGraph(navController: NavController) {
    composable(route = DestinationRoute.MY_BUSINESS_ROUTE) { backStackEntry->

        MyBusinessScreen(navController)
    }
}