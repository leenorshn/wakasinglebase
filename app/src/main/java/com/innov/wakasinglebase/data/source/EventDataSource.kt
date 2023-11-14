package com.innov.wakasinglebase.data.source


import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.EventModel
import com.innov.wakasinglebase.data.model.toEventModel
import com.wakabase.EventsQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by innov Victor on 4/3/2023.
 */
class TicketDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {



    fun fetchTickets(): Flow<BaseResponse<List<EventModel>>> {

        return flow {
            emit(BaseResponse.Loading)
           val resp= apolloClient.query(EventsQuery()).fetchPolicy(FetchPolicy.CacheAndNetwork).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of loading ticket check your network"))
            }
            val tickets=resp.data?.allEvents?.map {
                it.toEventModel()
            }?: emptyList()
            emit(BaseResponse.Success(tickets))
        }
    }
}