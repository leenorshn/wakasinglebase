package com.innov.wakasinglebase.domain.repository


import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.data.repository.video.VideoRepository
import com.innov.wakasinglebase.data.source.VideoDataSource
import com.innov.wakasinglebase.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class VideoRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val videoDataSource: VideoDataSource
) : VideoRepository {
    override fun getForYouPageFeeds(): Flow<List<VideoModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun createVideo(
        videoId: String,
        category: String,
        title: String,
        description: String,
        thumbnail:String,
    ): Flow<BaseResponse<Boolean>> {
        return videoDataSource.createVideo(
            videoId = videoId,
            category = category,
            title = title,
            description = description,
            thumbnail = thumbnail
        )
    }

    override suspend fun likeVideo(videoId: String): Flow<BaseResponse<Boolean>> {
        return  videoDataSource.likeVideo(videoId)
    }

    override suspend fun deleteVideo(videoId: String): Flow<BaseResponse<Boolean>> {
        return videoDataSource.deleteVideo(videoId)
    }

    override suspend fun getAllVideos(): Flow<BaseResponse<List<VideoModel>>> {
        return videoDataSource.fetchVideos(1000)
    }

    override suspend fun getAuthorVideos(authorId: String): Flow<BaseResponse<List<VideoModel>>> {
        return videoDataSource.fetchVideosOfParticularUser(authorId)
    }

    override suspend fun getVideo(videoId: String): Flow<BaseResponse<VideoModel>> {
        return  videoDataSource.fetchVideo(videoId)
    }

}