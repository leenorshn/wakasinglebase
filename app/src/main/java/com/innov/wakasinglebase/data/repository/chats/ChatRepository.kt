package com.innov.wakasinglebase.data.repository.chats

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.ConversationModel
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun getConversations(): Flow<BaseResponse<List<ConversationModel>>>
    suspend fun createConversation(receiver:String):Flow<BaseResponse<Boolean>>

    suspend fun sendMessage(conversation:String,message:String):Flow<BaseResponse<Boolean>>
}