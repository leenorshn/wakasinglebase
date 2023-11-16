package com.innov.wakasinglebase.data.model

data class ThreadModel(
    val id: String,
    val title: String,
    val theme: String,
    val coverImage: String,
    val author: UserModel,
    val createdAt: Long,
    val price: Double,
    val isArchived: Boolean,
    val isForSell: Boolean
)

data class Chapter(
    val id: String,
    val threadID: String,
    val name: String,
    val file: String,
    val isFree: Boolean,
)
