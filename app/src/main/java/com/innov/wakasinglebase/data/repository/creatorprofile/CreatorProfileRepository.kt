package com.innov.wakasinglebase.data.repository.creatorprofile

import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.data.source.UserDataSource
import com.innov.wakasinglebase.data.source.VideoDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by innov  on 3/22/2023.
 */
class CreatorProfileRepository @Inject constructor(
  private val dataSource: UserDataSource
) {
    suspend fun getCreatorDetails(id: String): Flow<UserModel?> {
        return dataSource.fetchSpecificUser(id)
    }

    suspend fun getCreatorPublicVideo(id: String): Flow<List<VideoModel>> {
        return dataSource.fetchVideosOfParticularUser(id)
    }
}