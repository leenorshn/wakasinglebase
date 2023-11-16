package com.innov.wakasinglebase.data.mapper

import com.innov.wakasinglebase.data.model.ThreadModel
import com.innov.wakasinglebase.data.model.UserModel
import com.wakabase.MyThreadsQuery
import com.wakabase.ThreadQuery
import com.wakabase.ThreadsQuery


fun ThreadsQuery.Thread.toThreadModel():ThreadModel{
    return ThreadModel(
        id=id,
        title=title,
        theme=theme,
        coverImage=coverImage,
        price=price,
        createdAt = createdAt.toLong(),
        isArchived = isArchived,
        isForSell = isForSell,
        author = UserModel(
            uid=author.id,
            name=author.name,
            profilePic = author.profilePic

        )
    )
}

fun MyThreadsQuery.MyThread.toThreadModel():ThreadModel{
    return ThreadModel(
        id=id,
        title=title,
        theme=theme,
        coverImage=coverImage,
        price=price,
        createdAt = createdAt.toLong(),
        isArchived = isArchived,
        isForSell = isForSell,
        author = UserModel(
            uid=author.id,
            name=author.name,
            profilePic = author.profilePic

        )
    )
}

fun ThreadQuery.Thread.toThreadModel():ThreadModel{
    return ThreadModel(
        id=id,
        title=title,
        theme=theme,
        coverImage=coverImage,
        price=price,
        createdAt = createdAt.toLong(),
        isArchived = isArchived,
        isForSell = isForSell,
        author = UserModel(
            uid=author.id,
            name=author.name,
            profilePic = author.profilePic

        )
    )
}