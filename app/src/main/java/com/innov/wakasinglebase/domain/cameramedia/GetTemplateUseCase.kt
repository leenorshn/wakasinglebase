package com.innov.wakasinglebase.domain.cameramedia

import com.innov.wakasinglebase.data.model.TemplateModel
import com.innov.wakasinglebase.data.repository.camermedia.TemplateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by innov Victor on 4/3/2023.
 */
class GetTemplateUseCase @Inject constructor(
    private val templateRepository: TemplateRepository
) {
    operator fun invoke(): Flow<List<TemplateModel>> {
        return templateRepository.getTemplates()
    }
}