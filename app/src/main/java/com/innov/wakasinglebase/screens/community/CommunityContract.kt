package com.innov.wakasinglebase.screens.community

import com.innov.wakasinglebase.data.model.CommunityModel

data class ViewState(
    val communities: List<CommunityModel> ?= null,
    val error:String?=null,
    val isLoading:Boolean=false
)

sealed class CommunityEvent {
    object OnCommunityLoadEvent:CommunityEvent()
}