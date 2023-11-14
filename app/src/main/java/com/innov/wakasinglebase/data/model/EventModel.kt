package com.innov.wakasinglebase.data.model

import com.wakabase.EventsQuery

data class EventModel(
    val id:String,
    val name:String,
    val price:Double,
    val image:String,
    val detail:String?,
    val status:String
)

fun EventsQuery.AllEvent.toEventModel():EventModel{
    return  EventModel(id=id,name=name, price=price, image=image , status=status, detail =detail )
}


