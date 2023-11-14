package com.innov.wakasinglebase.data.model

import com.wakabase.FollowersQuery

data class FriendModel(
    val id:String,
    val name:String,
    val phone:String,
    val profilePic:String,
    val bio:String
)

fun FollowersQuery.Follower
        .toFriendModel():FriendModel{
    return FriendModel(id,name,phone,profilePic?:"https://i.pinimg.com/564x/a8/57/00/a85700f3c614f6313750b9d8196c08f5.jpg",bio)
}
