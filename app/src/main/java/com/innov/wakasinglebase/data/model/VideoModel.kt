package com.innov.wakasinglebase.data.model

import com.innov.wakasinglebase.core.extension.formattedCount
import com.innov.wakasinglebase.core.extension.randomUploadDate

/**
 * Created by innov  on 3/18/2023.
 */
data class VideoModel(
    val videoId: String,
    val authorDetails: UserModel?,
    val videoTitle:String? = "unDefined",
    val videoLink: String,
    val description: String,
    val like: Int,
    val view:Int,
    val comment: Int,
    val category: String,
    val currentViewerInteraction: ViewerInteraction = ViewerInteraction(),
    val createdAt: String = randomUploadDate(),
    val audioModel: AudioModel? = null,
    val hasTag: List<String> = listOf(),
) {

        var formattedLikeCount: String = ""
        var formattedCommentCount: String = ""
        var formattedShareCount: String = ""

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