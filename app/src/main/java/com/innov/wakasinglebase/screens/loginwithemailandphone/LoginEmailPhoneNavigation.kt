package com.innov.wakasinglebase.screens.loginwithemailandphone

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

/**
 * Created by innov Victor on 3/27/2023.
 */
fun NavGraphBuilder.loginEmailPhoneNavGraph(navController: NavController) {
    composable(route = DestinationRoute.LOGIN_OR_SIGNUP_WITH_PHONE_EMAIL_ROUTE) {
        LoginWithEmailPhoneScreen()
    }
}