package com.innov.wakasinglebase.data.model

import com.wakabase.TicketsQuery

data class TicketModel(
    val id:String,
    val name:String,
    val price:Double,
    val goal:Double,
    //val detail:String?,
    val status:String
)

fun TicketsQuery.AllTicket.toTicketModel():TicketModel{
    return  TicketModel(id=id,name=name, price=price, goal=goal , status=status)
}

data class LottedTicketModel(
    val id:String,
    val author:UserModel,
    val video:VideoModel,
    val ticket:TicketModel,
    val amount:Int
)
