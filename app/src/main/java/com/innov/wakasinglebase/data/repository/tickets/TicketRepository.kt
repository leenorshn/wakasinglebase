package com.innov.wakasinglebase.data.repository.tickets

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.LottedTicketModel
import com.innov.wakasinglebase.data.model.TicketModel
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    suspend fun getAllTicket(): Flow<BaseResponse<List<TicketModel>>>
    suspend fun buyTicket(
        ticket: String,
        author: String,
        video: String,
        amount: Int
    ): Flow<BaseResponse<Boolean>>

    suspend fun getMyLottedTicket():Flow<BaseResponse<LottedTicketModel>>
}