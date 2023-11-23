package com.innov.wakasinglebase.screens.createprofile.creatorvideo

import com.innov.wakasinglebase.data.model.VideoModel

/**
 * Created by innov Victor on 3/22/2023.
 */
data class ViewState(
    val isLoading: Boolean? = null,
    val error: String? = null,
    val creatorVideosList: List<VideoModel>? = null
)

sealed class CreatorVideoEvent {
    data class OnLikeVideoEvent(val videoId:String):CreatorVideoEvent()
}

