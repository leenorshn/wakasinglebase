package com.innov.wakasinglebase.screens.comment

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.innov.wakasinglebase.core.DestinationRoute.COMMENT_BOTTOM_SHEET_ROUTE

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.commentListingNavGraph(navController: NavController) {
    bottomSheet(route = COMMENT_BOTTOM_SHEET_ROUTE) {
        CommentListScreen(onClickCancel = { navController.navigateUp() })
    }
}