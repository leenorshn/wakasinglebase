package com.innov.wakasinglebase.screens.competition.watch

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.watchCompetitionNavGraph(navController: NavController) {

    composable(route = DestinationRoute.WATCH_COMPETITION_ROUTE) { backStackEntry->
        val id =  backStackEntry.arguments?.getString("id")
        WatchCompetitionScreen(navController = navController, id ="$id" )
    }
}