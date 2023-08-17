package com.innov.wakasinglebase.data.source


import com.innov.wakasinglebase.data.model.ContentCreatorFollowingModel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


object ContentCreatorForFollowingDataSource {

    suspend fun fetchContentCreatorForFollowing(): Flow<List<ContentCreatorFollowingModel>> {
        var cover:List<ContentCreatorFollowingModel> = emptyList()
        VideoDataSource.fetchVideos().collect{
         cover=   it.map {d->
                ContentCreatorFollowingModel(
                    userModel =d.authorDetails,
                    coverVideo = d
                )
            }
        }
        return flow {
            emit(cover.shuffled())
        }

    }



}