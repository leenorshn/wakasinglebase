package com.innov.wakasinglebase.domain

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.ConversationModel
import com.innov.wakasinglebase.data.repository.chats.ChatRepository
import com.innov.wakasinglebase.data.source.ChatDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val dataSource: ChatDataSource
):ChatRepository {
    override suspend fun getConversations(): Flow<BaseResponse<List<ConversationModel>>> {
        return  dataSource.getConversations()
    }

    override suspend fun createConversation(receiver: String): Flow<BaseResponse<Boolean>> {
        return dataSource.createConversation(receiver)
    }

    override suspend fun sendMessage(
        conversation: String,
        message: String
    ): Flow<BaseResponse<Boolean>> {
        return  dataSource.sendMessage(conversationId=conversation,message=message)
    }
}