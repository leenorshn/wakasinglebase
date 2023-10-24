package com.innov.wakasinglebase.signin.phoneScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.phoneNavGraph(navController: NavController) {
    composable(route = DestinationRoute.LOGIN_OR_SIGNUP_WITH_PHONE_ROUTE) {
        PhoneScreen(navController)
    }
}