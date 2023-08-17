package com.innov.wakasinglebase.data.repository.camermedia

import com.innov.wakasinglebase.data.model.TemplateModel
import com.innov.wakasinglebase.data.source.TemplateDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by innov  on 4/3/2023.
 */
class TemplateRepository @Inject constructor() {
    fun getTemplates(): Flow<List<TemplateModel>> {
        return TemplateDataSource.fetchTemplates()
    }


}