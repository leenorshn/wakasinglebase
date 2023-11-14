package com.innov.wakasinglebase.screens.home.video_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.common.WakawakaVerticalVideoPager
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@Composable
fun VideoDetailScreen(
    navController: NavController,
viewModel: VideoDetailViewModel= hiltViewModel()
) {
    val uiState by viewModel.state

    var videos= mutableListOf<VideoModel>()



    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            if (uiState.isLoading){
                CircularProgressIndicator(
                    color = PrimaryColor
                )
            }
            if (uiState.error!=null){
                Text(text="Error video not found")
            }
            if(uiState.video!=null){
                videos.add(uiState.video!!)
                WakawakaVerticalVideoPager(
                    videos = videos,
                    onclickComment = { navController.navigate(DestinationRoute.COMMENT_BOTTOM_SHEET_ROUTE) },
                    onClickLike = { s: String, b: Boolean -> },
                    onclickFavourite = {},
                    onClickAudio = {},
                    onClickVote = {},
                    onClickUser = { userId -> navController.navigate("${DestinationRoute.CREATOR_PROFILE_ROUTE}/$userId") }
                )
            }
        }
    }
}