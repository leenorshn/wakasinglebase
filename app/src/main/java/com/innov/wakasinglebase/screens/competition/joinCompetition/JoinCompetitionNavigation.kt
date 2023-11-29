package com.innov.wakasinglebase.screens.competition.joinCompetition

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.joinCompetitionNavGraph(navController: NavController) {

    composable(route = DestinationRoute.JOIN_COMPETITION_ROUTE) {backStackEntry->
        val id =  backStackEntry.arguments?.getString("id")
        JoinCompetitionScreen(navController = navController, id ="$id" )
    }
}