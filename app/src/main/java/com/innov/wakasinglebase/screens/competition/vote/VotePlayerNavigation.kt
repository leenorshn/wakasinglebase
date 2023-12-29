package com.innov.wakasinglebase.screens.competition.vote

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.AuthState
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.signin.SignInScreen

fun NavGraphBuilder.voteCompetitionNavGraph(navController: NavController,authState: AuthState) {

    composable(route = DestinationRoute.VOTE_COMPETITION_ROUTE) { backStackEntry->
        val id =  backStackEntry.arguments?.getString("id")
        VotePlayScreen( navController,id ="$id" )

    }
}