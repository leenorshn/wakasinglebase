package com.innov.wakasinglebase.screens.myprofil.myvideos

import com.innov.wakasinglebase.data.model.VideoModel


sealed class MyVideoEvent{
    data class OnVideosLoadedEvent(val uid: String):MyVideoEvent()
    data class OnVideoDeletedEvent(val id:String):MyVideoEvent()
}

data class ViewState(
    val videos:List<VideoModel> = emptyList(),
    val error:String?=null,
    val isLoading:Boolean=false
)

data class DeleteState(
    val success:Boolean=false,
    val error:String?=null,
    val isLoading:Boolean=false
)