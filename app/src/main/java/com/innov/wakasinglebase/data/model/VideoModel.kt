package com.innov.wakasinglebase.data.model

import com.innov.wakasinglebase.core.extension.formattedCount
import com.innov.wakasinglebase.core.extension.randomUploadDate
import com.innov.wakasinglebase.core.utils.FileUtils.convertUnixTimestampToReadableDate
import com.wakabase.CompetitionQuery
import com.wakabase.CompetitionsQuery

/**
 * Created by innov  on 3/18/2023.
 */
data class VideoModel(
    val videoId: String,
    val authorDetails: UserModel?=null,
    val videoTitle:String? = "unDefined",
    val videoLink: String,
    val description: String?,
    val like: Int,
    val view:Int,
    val comment: Int,
    val thumbnail:String?=null,
    val category: String?=null,
    val product:String?=null,
    val currentViewerInteraction: ViewerInteraction = ViewerInteraction(),
    val createdAt: String?=null,
    val audioModel: AudioModel? = null,
    val hasTag: List<String>? = listOf(),
) {

        var formattedLikeCount: String = ""
        var formattedCommentCount: String = ""
        var formattedViewsCount: String = ""

        init {
            formattedLikeCount = like.formattedCount()
            formattedCommentCount = comment.formattedCount()
            formattedViewsCount = view.formattedCount()
        }

    data class ViewerInteraction(
        var isLikedByYou: Boolean = false,
        var isAddedToFavourite: Boolean = false
    )
}

fun CompetitionsQuery.Video.toVideoModel():VideoModel{
    return VideoModel(
        videoId =id,
        videoLink = link,
        like = like,
        comment = 0,
        view = 0,
        category = category,
        thumbnail = thumbnail,
        videoTitle = title,
        description = "",
        product = product,
        createdAt = randomUploadDate(),
        hasTag = null,
    )
}

fun CompetitionQuery.Video.toVideoModel():VideoModel{
    return VideoModel(
        videoId =id,
        videoLink = link,
        like = like,
        comment = 0,
        view = 0,
        thumbnail = thumbnail,
        category = category,
        authorDetails = UserModel(
            uid=author.id,
            name= author.name,
            profilePic = author.profilePic
        ),
        videoTitle = title,
        description = "",
        product = product,
        createdAt = convertUnixTimestampToReadableDate(like.toLong()),
        hasTag = null,
    )
}