package com.innov.wakasinglebase.signin

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.screens.myprofil.MyProfileScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    composable(route = DestinationRoute.AUTHENTICATION_ROUTE) {
       // AuthenticationScreen(navController)
    }
}