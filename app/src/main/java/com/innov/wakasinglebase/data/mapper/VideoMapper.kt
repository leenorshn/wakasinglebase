package com.innov.wakasinglebase.data.mapper

import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.model.VideoModel
import com.wakabase.VideosQuery

fun VideosQuery.VideoStats.toVideoStats():VideoModel.VideoStats{
    return VideoModel.VideoStats(
        like=like,
        views=views,
        comment = comment?:0,
        share = share
    )
}



fun VideosQuery.Author.toAuthor():UserModel{
    return UserModel(
        uid = id,
        name =name,
        uniqueUserName=name.replace(" ","_"),
        phone = phone,
        profilePic = profilePic,
        balance = balance,
        bio=bio,
        isVerified = isVerified?:false,
        hasContract = hasContract?:false
    )
}
fun VideosQuery.Video.toVideoModel():VideoModel{
    return VideoModel(
        videoId =id,
        videoLink = link,
        videoStats = videoStats?.toVideoStats(),
        videoTitle = title,
        description = description,
        hasTag = hasTag,
        createdAt = "$createdAt",
        authorDetails = author.toAuthor()
    )
}