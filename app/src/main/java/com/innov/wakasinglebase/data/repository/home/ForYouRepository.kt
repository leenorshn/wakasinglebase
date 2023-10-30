package com.innov.wakasinglebase.data.repository.home

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.data.source.VideoDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by innov  on 3/15/2023.
 */
class ForYouRepository @Inject constructor(
    private val dataSource: VideoDataSource
) {
     suspend fun getForYouPageFeeds(): Flow<BaseResponse<List<VideoModel>>> {
        return dataSource.fetchVideos(10)
    }
}