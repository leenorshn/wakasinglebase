package com.innov.wakasinglebase.data.model

import com.wakabase.CommunitiesQuery
import com.wakabase.CommunityQuery

data class CommunityModel(
    val id:String,
    val name:String,
    val philosophy:String,
    val logo:String?,
    val members:List<UserModel>,
    val admins:List<UserModel>,
    val messages:List<MessageModel>,
    val subscription:Double,
    val createdAt:Long
)

data class MessageModel(
    val id:String,
    val audio:String,
    val text:String?,
    val sender:UserModel,
    val createdAt:Long
)

fun CommunityQuery.Community.toCommunityModel():CommunityModel{
    return  CommunityModel(
        id=id,
        name=name,
        philosophy=philosophy,
        logo=logo,
        admins=admins.map {
            UserModel(
                uid = it.id,
                name=it.name,
                profilePic = it.profilePic
            )
        },
        members = members.map {
            UserModel(
                uid = it.id,
                name=it.name,
                profilePic = it.profilePic
            )
        },
        messages = messages.map {
            MessageModel(
                id=it.id,
                audio = it.audio?:"",
                text = it.text,
                sender = UserModel(
                    uid = it.sender.id,
                    name=it.sender.name,
                    profilePic = it.sender.profilePic
                ),
                createdAt=createdAt.toLong()
            )
        },
        createdAt = createdAt.toLong(),
        subscription = subscription
    )
}
fun CommunitiesQuery.Community.toCommunityModel():CommunityModel{
    return  CommunityModel(
        id=id,
        name=name,
        philosophy=philosophy,
        logo=logo,
        admins=admins.map {
            UserModel(
                uid = it.id,
                name=it.name,
                profilePic = it.profilePic
            )
        },
        members = members.map {
            UserModel(
                uid = it.id,
                name=it.name,
                profilePic = it.profilePic
            )
        },
        messages = messages.map {
            MessageModel(
                id=it.id,
                audio = it.audio?:"",
                text = it.text,
                sender = UserModel(
                    uid = it.sender.id,
                    name=it.sender.name,
                    profilePic = it.sender.profilePic
                ),
                createdAt=createdAt.toLong()
            )
        },
        createdAt = createdAt.toLong(),
        subscription = subscription,
    )
}