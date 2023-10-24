package com.innov.wakasinglebase.signin.opt_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.optNavGraph(navController: NavController) {
    composable(route = DestinationRoute.OPT_SCREEN_ROUTE) {backStackEntry->

            val phone =  backStackEntry.arguments?.getString("phone")
            OptScreen(navController,phone=phone)

    }
}