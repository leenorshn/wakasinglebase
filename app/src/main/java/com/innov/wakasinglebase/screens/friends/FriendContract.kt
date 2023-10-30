package com.innov.wakasinglebase.screens.friends

import com.innov.wakasinglebase.data.model.FriendModel

data class ViewState(
    val friends: List<FriendModel> = emptyList(),
    val error:String?=null,
    val isLoading:Boolean=false
)

sealed class FriendEvent {
    object OnFriendLoadEvent:FriendEvent()
}