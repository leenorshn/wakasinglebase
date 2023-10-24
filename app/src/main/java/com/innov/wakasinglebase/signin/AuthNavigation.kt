package com.innov.wakasinglebase.signin

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.core.DestinationRoute.LOGIN_OR_SIGNUP_WITH_PHONE_ROUTE

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    composable(route = DestinationRoute.AUTHENTICATION_ROUTE) {
        SignInScreen(navController, onSignInClick = {
            navController.navigate(LOGIN_OR_SIGNUP_WITH_PHONE_ROUTE)
        })
    }
}