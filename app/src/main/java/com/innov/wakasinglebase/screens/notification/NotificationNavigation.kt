package com.innov.wakasinglebase.screens.notification

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.notificationNavGraph(navController: NavController) {
    composable(route = DestinationRoute.NOTIFICATION_ROUTE) {
        NotificationScreen(navController)
    }
}