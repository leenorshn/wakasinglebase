package com.innov.wakasinglebase.data.repository.video

import com.google.firebase.firestore.FirebaseFirestore
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.di.IoDispatcher
import com.innov.wakasinglebase.domain.repository.VideoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import video.api.client.api.models.Video
import javax.inject.Inject

class VideoInterfaceImpl @Inject constructor(
    private val db:FirebaseFirestore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
):VideoRepository {
    override fun getForYouPageFeeds(): Flow<List<VideoModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun createVideo(video: Video, author: UserModel): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun getAllVideos(): Flow<List<VideoModel>> {
        TODO("Not yet implemented")
    }

    override fun getAuthorVideos(authorId: String): Flow<List<VideoModel>> {
        TODO("Not yet implemented")
    }

    override fun getVideo(videoId: String): Flow<VideoModel> {
        TODO("Not yet implemented")
    }
}