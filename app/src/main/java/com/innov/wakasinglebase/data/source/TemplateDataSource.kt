package com.innov.wakasinglebase.data.source


import com.apollographql.apollo3.ApolloClient
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.TicketModel
import com.innov.wakasinglebase.data.model.toTicketModel
import com.wakabase.CreateLotTicketMutation
import com.wakabase.TicketsQuery
import com.wakabase.type.NewLottedTicket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by innov Victor on 4/3/2023.
 */
class TicketDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {

   fun buyTicket(ticket:String,author:String,video:String,amount:Int):Flow<BaseResponse<Boolean>>{
       return flow{
           emit(BaseResponse.Loading)
           val resp=apolloClient.mutation(CreateLotTicketMutation(NewLottedTicket(
               ticket=ticket,
               author=author,
               video = video,
               amount = amount
           ))).execute()

           if (resp.hasErrors()){
               emit(BaseResponse.Error("Impossible to buy a ticket"))
           }
           emit(BaseResponse.Success(true))
       }.catch {
           emit(BaseResponse.Error("Impossible to buy a ticket, unknown error!"))
       }
   }
 

    fun fetchTickets(): Flow<BaseResponse<List<TicketModel>>> {

        return flow {
            emit(BaseResponse.Loading)
           val resp= apolloClient.query(TicketsQuery()).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of loading ticket check your network"))
            }
            val tickets=resp.data?.allTicket?.map {
                it.toTicketModel()
            }?: emptyList()
            emit(BaseResponse.Success(tickets))
        }
    }
}