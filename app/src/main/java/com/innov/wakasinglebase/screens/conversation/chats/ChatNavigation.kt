package com.innov.wakasinglebase.screens.conversation.chats

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.chatNavGraph(navController: NavController) {
    composable(route = DestinationRoute.CHAT_ROUTE) {
        ChatScreen(navController)
    }
}