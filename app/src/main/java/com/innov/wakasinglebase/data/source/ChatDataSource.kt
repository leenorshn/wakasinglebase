package com.innov.wakasinglebase.data.source

import com.apollographql.apollo3.ApolloClient
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.ConversationModel
import com.innov.wakasinglebase.data.model.toConversationModel
import com.wakabase.ChatsQuery
import com.wakabase.CreateConversationMutation
import com.wakabase.SendMessageMutation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChatDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {

    fun createConversation(receiverId:String):Flow<BaseResponse<Boolean>>{
        return flow {
            emit(BaseResponse.Loading)
            var res=apolloClient.mutation(CreateConversationMutation(receiverId)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error of creating a conversation with $receiverId"))
            }
            else{
                emit(BaseResponse.Success(true))
            }
        }

    }

    fun sendMessage(conversationId:String,message:String):Flow<BaseResponse<Boolean>>{
        return flow {
            emit(BaseResponse.Loading)
            var res=apolloClient.mutation(SendMessageMutation(conversationId,message)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error of creating a message"))
            }
            else{
                emit(BaseResponse.Success(true))
            }
        }
    }

    fun getConversations():Flow<BaseResponse<List<ConversationModel>>>{
        return flow {
            emit(BaseResponse.Loading)
            var res=apolloClient.query(ChatsQuery()).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error of loading  message"))
            }
            else{
              val conves:List<ConversationModel> =  res.data?.conversations?.map { it.toConversationModel() }?: emptyList()
                emit(BaseResponse.Success(conves))
            }
        }
    }
}