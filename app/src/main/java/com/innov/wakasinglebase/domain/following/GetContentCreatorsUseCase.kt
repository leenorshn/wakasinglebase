package com.innov.wakasinglebase.domain.following

import com.innov.wakasinglebase.data.model.ContentCreatorFollowingModel
import com.innov.wakasinglebase.data.repository.home.FollowingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by innov Victor on 3/15/2023.
 */
class GetContentCreatorsUseCase @Inject constructor(private val followingRepository: FollowingRepository) {
    suspend operator fun invoke(): Flow<List<ContentCreatorFollowingModel>> {
        return followingRepository.getContentCreatorForFollowing()
    }
}