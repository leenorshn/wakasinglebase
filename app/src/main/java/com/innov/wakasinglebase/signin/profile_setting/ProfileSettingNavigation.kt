package com.innov.wakasinglebase.signin.profile_setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.profileSettingNavGraph(navController: NavController) {
    composable(route = DestinationRoute.PROFILE_SETTING) {
        ProfileSettingScreen(navController)
    }
}