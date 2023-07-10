package com.innov.wakasinglebase.data.repository.home

import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.data.source.VideoDataSource.fetchVideos
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by innov  on 3/15/2023.
 */
class ForYouRepository @Inject constructor() {
    fun getForYouPageFeeds(): Flow<List<VideoModel>> {
        return fetchVideos()
    }
}