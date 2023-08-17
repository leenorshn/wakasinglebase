package com.innov.wakasinglebase.data.repository.home

import com.innov.wakasinglebase.data.model.ContentCreatorFollowingModel
import com.innov.wakasinglebase.data.source.ContentCreatorForFollowingDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by innov  on 3/15/2023.
 */
class FollowingRepository @Inject constructor() {
   suspend fun getContentCreatorForFollowing(): Flow<List<ContentCreatorFollowingModel>> {
        return ContentCreatorForFollowingDataSource.fetchContentCreatorForFollowing()
    }
}