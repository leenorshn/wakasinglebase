package com.innov.wakasinglebase.screens.threads

import com.innov.wakasinglebase.data.model.ThreadModel

data class ViewState(
    val threads: List<ThreadModel> ?= null,
    val error:String?=null,
    val isLoading:Boolean=false
)

sealed class ThreadsEvent {
    object OnThreadsLoadEvent:ThreadsEvent()
}