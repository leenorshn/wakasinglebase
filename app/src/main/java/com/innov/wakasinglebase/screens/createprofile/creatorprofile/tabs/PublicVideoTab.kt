package com.innov.wakasinglebase.screens.createprofile.creatorprofile.tabs

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.screens.createprofile.creatorprofile.CreatorProfileViewModel
import com.innov.wakasinglebase.screens.createprofile.creatorprofile.VideoGrid

/**
 * Created by innov Victor on 3/25/2023.
 */

@Composable
fun PublicVideoTab(
    viewModel: CreatorProfileViewModel,
    scrollState: ScrollState,
    onClickVideo: (video: VideoModel, index: Int) -> Unit
) {
    val creatorPublicVideos by viewModel.publicVideosList.collectAsState()
    VideoGrid(
        scrollState = scrollState,
        videoList = creatorPublicVideos,
        onClickVideo = onClickVideo
    )
}