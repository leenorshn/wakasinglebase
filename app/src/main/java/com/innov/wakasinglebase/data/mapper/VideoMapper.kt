package com.innov.wakasinglebase.data.mapper

import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.model.VideoModel
import com.wakabase.VideosQuery





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
        videoTitle = title,
        description = description,
        hasTag = hasTag,
        thumbnail = thumbnail,
        createdAt = "$createdAt",
        category=category,
        authorDetails = author.toAuthor(),
        like = like,
        view = view,
        comment = comment
    )
}