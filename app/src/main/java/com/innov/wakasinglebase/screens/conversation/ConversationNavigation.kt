package com.innov.wakasinglebase.screens.conversation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute.LOTTO_ROUTE

/**
 * Created by innov Victor on 3/27/2023.
 */
fun NavGraphBuilder.marketNavGraph(navController: NavController) {
    composable(route = LOTTO_ROUTE) {
        MarketScreen(navController)
    }
}