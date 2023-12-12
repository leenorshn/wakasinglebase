package com.innov.wakasinglebase.data.model

import com.innov.wakasinglebase.core.utils.FileUtils
import com.wakabase.GetCommentsQuery

/**
 * Created by innov  on 3/21/2023.
 */

    data class CommentModel(
        val author: UserModel,
        val comment: String?,
        val createdAt: String,
    )
fun GetCommentsQuery.Author.toAuthor():UserModel{
    return UserModel(
        uid = id,
        name=name,
        profilePic = profilePic
    )
}

fun GetCommentsQuery.Comment.toCommentModel():CommentModel{
    return CommentModel(
        author=author.toAuthor(),
        comment=comment,
        createdAt= FileUtils.convertUnixTimestampToReadableDate(createdAt.toLong()),
    )
}


