package com.innov.wakasinglebase.screens.competition

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.AuthState
import com.innov.wakasinglebase.core.DestinationRoute.LOTTO_ROUTE

/**
 * Created by innov Victor on 3/27/2023.
 */
fun NavGraphBuilder.marketNavGraph(navController: NavController,authState: AuthState) {
    composable(route = LOTTO_ROUTE) {
       // if (authState.success){
            CompetitionScreen(navController,authState)

    }
}