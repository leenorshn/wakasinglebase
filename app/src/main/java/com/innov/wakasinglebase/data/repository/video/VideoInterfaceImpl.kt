package com.innov.wakasinglebase.data.repository.video

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.di.IoDispatcher
import com.innov.wakasinglebase.domain.repository.VideoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val db:FirebaseFirestore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
):VideoRepository {
    override fun getForYouPageFeeds(): Flow<List<VideoModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun createVideo(videoId: String, author: UserModel,description:String): Result<String> {
        val videoUp= mapOf(
            "videoId" to videoId,
            "videoLink" to "https://d2y4y6koqmb0v7.cloudfront.net/$videoId",
            "description" to "$description",
            "like" to 0,
            "share" to 0,
            "authorDetails" to author,
            "createdAt" to Timestamp.now().seconds
        )
      return try {
            db.collection("videos").add(videoUp).await()
           return Result.success("Done")
       }catch (ex:Exception){
           return Result.failure(ex)
       }
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