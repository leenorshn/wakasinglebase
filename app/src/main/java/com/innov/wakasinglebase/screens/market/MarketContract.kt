package com.innov.wakasinglebase.screens.market

import com.innov.wakasinglebase.data.model.TicketModel

data class ViewState(
    val tickets: List<TicketModel>? = null,
    val error:String?=null,
    val isLoading:Boolean=false
)

sealed class MarketMediaEvent {
    object EventFetchTemplate : MarketMediaEvent()


}