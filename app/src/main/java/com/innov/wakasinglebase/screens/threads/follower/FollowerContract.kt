package com.innov.wakasinglebase.screens.threads.follower

import com.innov.wakasinglebase.data.model.UserModel


data class ViewState(
    val listUser:List<UserModel>? =null ,
    val error:String?=null,
    val isLoading:Boolean=false,
    val followerList:List<String> = emptyList()
)

sealed class FollowerEvent{
    data class OnFollowingUser(val uid:String): FollowerEvent()
    object OnLoadUsers: FollowerEvent()


}

data class FollowState(
    val error:String?=null,
    val done:Boolean=false,
    val isLoading: Boolean=false,
)