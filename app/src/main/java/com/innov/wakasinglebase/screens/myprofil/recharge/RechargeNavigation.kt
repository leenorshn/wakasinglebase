package com.innov.wakasinglebase.screens.myprofil.recharge

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.rechargeNavGraph(navController: NavController) {
    composable(route = DestinationRoute.RECHARGE_ROUTE) {
        RechargeScreen(navController)
    }
}