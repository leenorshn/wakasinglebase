package com.innov.wakasinglebase.screens.myprofil

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

/**
 * Created by innov Victor on 4/1/2023.
 */
fun NavGraphBuilder.myProfileNavGraph(navController: NavController) {
    composable(route = DestinationRoute.MY_PROFILE_ROUTE) {
        MyProfileScreen(navController)
    }
}