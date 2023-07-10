package com.innov.wakasinglebase.screens.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

/**
 * Created by innov Victor on 4/1/2023.
 */
fun NavGraphBuilder.settingNavGraph(navController: NavController) {
    composable(route = DestinationRoute.SETTING_ROUTE) {
        SettingScreen(navController)
    }
}