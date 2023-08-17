package com.innov.wakasinglebase.domain.creatorprofile

import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.repository.creatorprofile.CreatorProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by innov Victor on 3/22/2023.
 */
class GetCreatorProfileUseCase @Inject constructor(
    private val creatorProfileRepository: CreatorProfileRepository
) {
    suspend operator fun invoke(id: String): Flow<UserModel?> {
        return creatorProfileRepository.getCreatorDetails(id)
    }
}