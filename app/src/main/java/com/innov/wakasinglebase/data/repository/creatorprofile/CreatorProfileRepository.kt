package com.innov.wakasinglebase.data.repository.creatorprofile

import com.innov.wakasinglebase.core.base.BaseResponse
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
  private val dataSource: UserDataSource,
    private val videoDataSource: VideoDataSource,
) {
    suspend fun getCreatorDetails(id: String): Flow<UserModel?> {
        return dataSource.fetchSpecificUser(id)
    }

    suspend fun getCreatorPublicVideo(id: String): Flow<BaseResponse<List<VideoModel>>> {
        return videoDataSource.fetchVideosOfParticularUser(id)
    }
}