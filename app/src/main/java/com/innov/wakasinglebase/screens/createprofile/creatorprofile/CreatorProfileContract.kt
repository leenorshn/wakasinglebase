package com.innov.wakasinglebase.screens.createprofile.creatorprofile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.data.model.UserModel

/**
 * Created by innov Victor on 3/22/2023.
 */
data class ViewState(
    val isLoading: Boolean? = null,
    val error: String? = null,
    val creatorProfile: UserModel? = null,
)

data class FollowState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
)

sealed class CreatorProfileEvent {
    data class OnFollowUser(val id:String):CreatorProfileEvent()
}


enum class ProfilePagerTabs(
    @StringRes val title: Int? = null,
    @DrawableRes val icon: Int
) {
    PUBLIC_VIDEO(icon = R.drawable.ic_list),
    LIKED_VIDEO(icon = R.drawable.ic_private_like)
}