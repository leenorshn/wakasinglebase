package com.innov.wakasinglebase.screens.market

import com.innov.wakasinglebase.data.model.EventModel

data class ViewState(
    val tickets: List<EventModel>? = null,
    val error:String?=null,
    val isLoading:Boolean=false
)

sealed class MarketMediaEvent {
    object EventFetchTemplate : MarketMediaEvent()


}