package com.innov.wakasinglebase.signin.follower

import com.innov.wakasinglebase.data.model.UserModel


data class ViewState(
    val listUser:List<UserModel>? =null ,
    val error:String?=null,
    val isLoading:Boolean=false,
    val followerList:List<UserModel> = emptyList()
)

sealed class FollowerEvent{
    data class OnFollowingUser(val user:UserModel):FollowerEvent()
    object OnLoadUsers:FollowerEvent()
}