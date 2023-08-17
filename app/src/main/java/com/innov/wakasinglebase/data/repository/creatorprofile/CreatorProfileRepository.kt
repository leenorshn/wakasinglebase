package com.innov.wakasinglebase.data.repository.creatorprofile

import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.data.source.UsersDataSource.fetchSpecificUser
import com.innov.wakasinglebase.data.source.VideoDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by innov  on 3/22/2023.
 */
class CreatorProfileRepository @Inject constructor() {
    suspend fun getCreatorDetails(id: String): Flow<UserModel?> {
        return fetchSpecificUser(id)
    }

    suspend fun getCreatorPublicVideo(id: String): Flow<List<VideoModel>> {
        return VideoDataSource.fetchVideosOfParticularUser(id)
    }
}