package com.innov.wakasinglebase.screens.following

import com.innov.wakasinglebase.data.model.ContentCreatorFollowingModel

/**
 * Created by innov Victor on 3/15/2023.
 */
data class ViewState(
    val isLoading: Boolean? = null,
    val error: String? = null,
    val contentCreators: List<ContentCreatorFollowingModel>? = null
)

sealed class FollowingEvent {
}