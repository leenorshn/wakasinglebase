package com.innov.wakasinglebase.screens.myprofil.monetisation


data class ViewState(
    val success:Boolean=false,
    val isLoading:Boolean=false,
    val error:String?=null
)

sealed class MonetisationEvent{
    object OnMonetisationSend:MonetisationEvent()
}