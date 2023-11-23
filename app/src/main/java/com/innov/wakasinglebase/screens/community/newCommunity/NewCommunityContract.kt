package com.innov.wakasinglebase.screens.community.newCommunity

import com.innov.wakasinglebase.data.model.UserModel


data class ViewState(
    val success:Boolean=false,
    val error:String?=null,
    val isLoading:Boolean=false,
    val friends:List<UserModel>?=null
)

data class NewCommunityState(
    val success:Boolean=false,
    val error:String?=null,
    val isLoading:Boolean=false,
)

sealed class NewCommunityEvent{
    data class OnNameEnteredEvent(val name:String):NewCommunityEvent()
    data class OnPhilosophyEnteredEvent(val philosophy:String):NewCommunityEvent()

    data class OnSubscriptionSelectedEvent(val subscription:Double):NewCommunityEvent()

    data class OnMembersEnteredEvent(val members:List<String>):NewCommunityEvent()

    object OnSubmitEvent:NewCommunityEvent()
    object OnLoadFriendsEvent:NewCommunityEvent()
}