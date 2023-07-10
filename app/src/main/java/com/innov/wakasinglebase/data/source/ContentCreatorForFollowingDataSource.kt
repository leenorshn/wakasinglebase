package com.innov.wakasinglebase.data.source


import com.innov.wakasinglebase.data.model.ContentCreatorFollowingModel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


object ContentCreatorForFollowingDataSource {

    fun fetchContentCreatorForFollowing(): Flow<List<ContentCreatorFollowingModel>> {
        return flow {
            val creatorForFollowing: List<ContentCreatorFollowingModel> = listOf(
                ContentCreatorFollowingModel(
                    userModel = UsersDataSource.kylieJenner,
                    coverVideo = VideoDataSource.KylieJennerVideos.kylie_vid1
                ),
            )
            emit(creatorForFollowing.shuffled())
        }

    }

}