package com.innov.wakasinglebase.screens.comment

import com.innov.wakasinglebase.data.model.CommentList
import com.innov.wakasinglebase.data.model.VideoModel

data class ViewState(
    val isLoading: Boolean? = null,
    val error: String? = null,
    val comments: CommentList? = null
)

sealed class CommentEvent {
    data class OnPublishEvent(
        val userId:String,
        val videoId:String,
        val comment:String,
    ):CommentEvent()

    data class OnLoadCommentsEvent(
        val video: VideoModel
    ):CommentEvent()
}

enum class HighlightedEmoji(val unicode: String = "") {
    LAUGHING_WITH_SMILING_EYES(unicode = "\uD83D\uDE01"),
    SMILING_WITH_HEART(unicode = "\uD83E\uDD70"),
    TEAR_OF_JOY(unicode = "\uD83D\uDE02"),
    FLUSHED_FACE(unicode = "\uD83D\uDE33"),
    SMIRKING_FACE(unicode = "\uD83D\uDE0F"),
    GRINNING_FACE(unicode = "\uD83D\uDE05"),
}