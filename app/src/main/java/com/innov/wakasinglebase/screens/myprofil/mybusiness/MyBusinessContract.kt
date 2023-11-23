package com.innov.wakasinglebase.screens.myprofil.mybusiness

import com.innov.wakasinglebase.data.model.ProductModel


data class ViewState(
    val myThreads:List<ProductModel>?=null,
    val isLoading:Boolean=false,
    val error:String?=null,
)

sealed class MyBusinessEvent{
    object LoadThreadsEvent:MyBusinessEvent()

}