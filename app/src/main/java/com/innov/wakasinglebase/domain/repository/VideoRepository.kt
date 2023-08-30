package com.innov.wakasinglebase.domain.repository

import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.model.VideoModel
import kotlinx.coroutines.flow.Flow


interface VideoRepository {
    fun getForYouPageFeeds(): Flow<List<VideoModel>>

    suspend  fun createVideo(videoId: String, author: UserModel,description:String):Result<String>
    fun getAllVideos(): Flow<List<VideoModel>>
    fun getAuthorVideos(authorId:String): Flow<List<VideoModel>>
    fun getVideo(videoId:String): Flow<VideoModel>
}