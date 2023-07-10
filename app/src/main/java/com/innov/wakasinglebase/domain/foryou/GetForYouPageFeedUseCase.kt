package com.innov.wakasinglebase.domain.foryou

import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.data.repository.home.ForYouRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by innov Victor on 3/15/2023.
 */
class GetForYouPageFeedUseCase @Inject constructor(private val forYouRepository: ForYouRepository) {
    operator fun invoke(): Flow<List<VideoModel>> {
        return forYouRepository.getForYouPageFeeds()
    }
}