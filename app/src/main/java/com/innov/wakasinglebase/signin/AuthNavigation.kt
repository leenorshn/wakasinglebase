package com.innov.wakasinglebase.signin

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.AuthState
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.core.DestinationRoute.LOGIN_OR_SIGNUP_WITH_PHONE_ROUTE

fun NavGraphBuilder.authNavGraph(navController: NavController,authState: AuthState) {
    composable(route = DestinationRoute.AUTHENTICATION_ROUTE) {
        SignInScreen(navController,authState=authState, onSignInClick = {
            navController.navigate(LOGIN_OR_SIGNUP_WITH_PHONE_ROUTE)
        })
    }
}