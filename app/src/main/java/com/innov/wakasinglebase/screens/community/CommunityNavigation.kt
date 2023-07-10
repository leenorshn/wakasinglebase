package com.innov.wakasinglebase.screens.community

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute.GIFT_ROUTE

/**
 * Created by innov Victor on 3/27/2023.
 */
fun NavGraphBuilder.communityNavGraph(navController: NavController) {
    composable(route = GIFT_ROUTE) {
        CommunityScreen(navController)
    }
}