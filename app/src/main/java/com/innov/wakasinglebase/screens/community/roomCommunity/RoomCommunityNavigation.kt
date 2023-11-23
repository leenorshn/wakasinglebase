package com.innov.wakasinglebase.screens.community.roomCommunity

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.core.DestinationRoute

fun NavGraphBuilder.roomCommunityNavGraph(navController: NavController) {
    composable(route = DestinationRoute.ROOM_COMMUNITY_ROUTE) {backStackEntry->
        val videoId =  backStackEntry.arguments?.getString("community")
        RoomCommunityScreen(navController,communityId = videoId)
    }
}
