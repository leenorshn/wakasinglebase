package com.innov.wakasinglebase.screens.createprofile.creatorprofile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.innov.wakasinglebase.core.DestinationRoute.COMMENT_BOTTOM_SHEET_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.CREATOR_PROFILE_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.FORMATTED_COMPLETE_CREATOR_VIDEO_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.PassedKey.USER_ID
import com.innov.wakasinglebase.core.DestinationRoute.PassedKey.VIDEO_INDEX
import com.innov.wakasinglebase.screens.createprofile.creatorprofile.CreatorProfileScreen
import com.innov.wakasinglebase.screens.createprofile.creatorvideo.CreatorVideoPagerScreen


/**
 * Created by innov Victor on 3/22/2023.
 */

fun NavGraphBuilder.creatorProfileNavGraph(navController: NavController) {
    composable(route = "$CREATOR_PROFILE_ROUTE/{$USER_ID}",
        arguments = listOf(
            navArgument(USER_ID) { type = NavType.StringType }
        )
    ) {
        CreatorProfileScreen(
            onClickNavIcon = { navController.navigateUp() },
            navController = navController
        )
    }

    composable(route = FORMATTED_COMPLETE_CREATOR_VIDEO_ROUTE,
        arguments = listOf(
            navArgument(USER_ID) { type = NavType.StringType },
            navArgument(VIDEO_INDEX) { type = NavType.IntType }
        )
    ) {
        CreatorVideoPagerScreen(
            onClickNavIcon = { navController.navigateUp() },
            onclickComment = { navController.navigate(COMMENT_BOTTOM_SHEET_ROUTE) },
            onClickAudio = {},
            onClickUser = { navController.navigateUp() }
        )
    }
}

