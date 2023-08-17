package com.innov.wakasinglebase.domain.repository

import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.model.VideoModel
import kotlinx.coroutines.flow.Flow
import video.api.client.api.models.Video

interface VideoRepository {
    fun getForYouPageFeeds(): Flow<List<VideoModel>>

    suspend  fun createVideo(video: Video, author: UserModel):Result<Unit>
    fun getAllVideos(): Flow<List<VideoModel>>
    fun getAuthorVideos(authorId:String): Flow<List<VideoModel>>
    fun getVideo(videoId:String): Flow<VideoModel>
}