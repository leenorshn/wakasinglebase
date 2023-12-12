package com.innov.wakasinglebase.screens.competition.vote

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.voteCompetitionNavGraph(navController: NavController) {

    composable(route = DestinationRoute.VOTE_COMPETITION_ROUTE) { backStackEntry->
        val id =  backStackEntry.arguments?.getString("id")
        VotePlayScreen( navController,id ="$id" )
    }
}