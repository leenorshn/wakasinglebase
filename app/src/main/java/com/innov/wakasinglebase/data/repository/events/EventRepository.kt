package com.innov.wakasinglebase.data.repository.events

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.EventModel
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun getAllEvents(): Flow<BaseResponse<List<EventModel>>>

}