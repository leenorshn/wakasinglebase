package com.innov.wakasinglebase.screens.community.roomCommunity

import com.innov.wakasinglebase.data.model.CommunityModel


data class ViewState(
    val community:CommunityModel? =null ,
    val error:String?=null,
    val isLoading:Boolean=false,

)

sealed class RoomCommunityEvent{

    data class OnLoadCommunity(val id:String): RoomCommunityEvent()


}

data class FollowState(
    val error:String?=null,
    val done:Boolean=false,
    val isLoading: Boolean=false,
)