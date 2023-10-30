package com.innov.wakasinglebase.data.model

import com.innov.wakasinglebase.core.extension.formattedCount
import com.innov.wakasinglebase.core.extension.randomUploadDate

/**
 * Created by innov  on 3/18/2023.
 */
data class VideoModel(
    val videoId: String,
    val authorDetails: UserModel?,
    val videoStats: VideoStats?,
    val videoTitle:String? = "unDefined",
    val videoLink: String,
    val description: String,
    val currentViewerInteraction: ViewerInteraction = ViewerInteraction(),
    val createdAt: String = randomUploadDate(),
    val audioModel: AudioModel? = null,
    val hasTag: List<String> = listOf(),
) {
    data class VideoStats(
        var like: Int,
        var comment: Int,
        var share: Int,

        var views: Int = (like.plus(500)..like.plus(100000)).random()
    ) {
        var formattedLikeCount: String = ""
        var formattedCommentCount: String = ""
        var formattedShareCount: String = ""

        var formattedViewsCount: String = ""

        init {
            formattedLikeCount = like.formattedCount()
            formattedCommentCount = comment.formattedCount()
            formattedShareCount = share.formattedCount()

            formattedViewsCount = views.formattedCount()
        }
    }

    data class HasTag(
        val id: String?,
        val title: String
    )

    data class ViewerInteraction(
        var isLikedByYou: Boolean = false,
        var isAddedToFavourite: Boolean = false
    )
}