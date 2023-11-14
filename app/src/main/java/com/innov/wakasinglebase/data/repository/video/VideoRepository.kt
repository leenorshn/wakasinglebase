package com.innov.wakasinglebase.data.repository.video

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.VideoModel
import kotlinx.coroutines.flow.Flow


interface VideoRepository {
    fun getForYouPageFeeds(): Flow<List<VideoModel>>

    suspend fun createVideo(
        videoId: String,
        category: String,
        title: String,
       // author: UserModel,
        description: String,
    ): Flow<BaseResponse<Boolean>>

    suspend fun getAllVideos(): Flow<BaseResponse<List<VideoModel>>>
   suspend fun getAuthorVideos(authorId: String): Flow<BaseResponse<List<VideoModel>>>
   suspend fun getVideo(videoId: String): Flow<BaseResponse<VideoModel>>
}