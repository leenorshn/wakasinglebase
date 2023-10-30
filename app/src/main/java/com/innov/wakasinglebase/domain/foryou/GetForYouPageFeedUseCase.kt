package com.innov.wakasinglebase.domain.foryou

import com.innov.wakasinglebase.data.repository.home.ForYouRepository
import javax.inject.Inject

/**
 * Created by innov Victor on 3/15/2023.
 */
class VideoPageFeedUseCase @Inject constructor(private val videoRepository: ForYouRepository) {
//     suspend operator fun invoke(): Flow<List<VideoModel>> {
//        return videoRepository.getForYouPageFeeds()
//    }
}