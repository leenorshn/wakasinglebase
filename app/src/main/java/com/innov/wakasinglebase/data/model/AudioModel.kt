package com.innov.wakasinglebase.data.model

/**
 * Created by innov  on 3/18/2023.
 */
data class AudioModel(
    val audioCoverImage:String,
    val isOriginal:Boolean,
    val audioAuthor:UserModel,
    val numberOfPost:Long,
    val originalVideoUrl:String,
)