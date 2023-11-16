package com.innov.wakasinglebase.screens.myprofil.mybusiness

import com.innov.wakasinglebase.data.model.ThreadModel


data class ViewState(
    val myThreads:List<ThreadModel>?=null,
    val isLoading:Boolean=false,
    val error:String?=null,
)

sealed class MyBusinessEvent{
    object LoadThreadsEvent:MyBusinessEvent()

}