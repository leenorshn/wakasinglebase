package com.innov.wakasinglebase.screens.home.foryou

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.common.WakawakaVerticalVideoPager
import com.innov.wakasinglebase.core.DestinationRoute.CREATOR_PROFILE_ROUTE
import com.innov.wakasinglebase.ui.theme.DarkBlue
import com.innov.wakasinglebase.ui.theme.DarkPink
import com.innov.wakasinglebase.core.DestinationRoute.COMMENT_BOTTOM_SHEET_ROUTE

/**
 * Created by innov Victor on 3/14/2023.
 */
@Composable
fun ForYouTabScreen(
    navController: NavController,
    viewModel: ForYouViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(DarkPink, DarkBlue)
                )
            )
    ) {

        viewState?.forYouPageFeed?.let {

           WakawakaVerticalVideoPager(
                videos = it,
                onclickComment = { navController.navigate(COMMENT_BOTTOM_SHEET_ROUTE) },
                onClickLike = { s: String, b: Boolean -> },
                onclickFavourite = {},
                onClickAudio = {},
                onClickUser = { userId -> navController.navigate("$CREATOR_PROFILE_ROUTE/$userId") }
            )
        }
    }
}
