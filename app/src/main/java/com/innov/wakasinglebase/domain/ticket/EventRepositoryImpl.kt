package com.innov.wakasinglebase.domain.ticket

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.EventModel
import com.innov.wakasinglebase.data.repository.events.EventRepository
import com.innov.wakasinglebase.data.source.TicketDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val dataSource: TicketDataSource
) : EventRepository {
    override suspend fun getAllEvents(): Flow<BaseResponse<List<EventModel>>> {
        return  dataSource.fetchTickets()
    }



}