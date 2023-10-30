package com.innov.wakasinglebase.domain.ticket

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.LottedTicketModel
import com.innov.wakasinglebase.data.model.TicketModel
import com.innov.wakasinglebase.data.repository.tickets.TicketRepository
import com.innov.wakasinglebase.data.source.TicketDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val dataSource: TicketDataSource
) :TicketRepository{
    override suspend fun getAllTicket(): Flow<BaseResponse<List<TicketModel>>>{
        return  dataSource.fetchTickets()
    }

    override suspend fun buyTicket(
        ticket: String,
        author: String,
        video: String,
        amount: Int
    ): Flow<BaseResponse<Boolean>> {
        return dataSource.buyTicket(ticket,author,video,amount)
    }

    override suspend fun getMyLottedTicket(): Flow<BaseResponse<LottedTicketModel>> {
        TODO("Not yet implemented")
    }
}