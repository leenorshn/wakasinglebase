package com.innov.wakasinglebase.screens.conversation

import com.innov.wakasinglebase.data.model.ConversationModel
import com.innov.wakasinglebase.data.model.UserModel

data class ViewState(
    val users: List<UserModel>? = null,
    val error:String?=null,
    val isLoading:Boolean=false
)

data class ChatViewState(
    val conversations: List<ConversationModel>? = null,
    val error:String?=null,
    val isLoading:Boolean=false
)

data class ConversationState(
    val success:Boolean=false,
    val error:String?=null,
    val isLoading:Boolean=false
)

sealed class ConversationEvent {
    object EventFetchUserEvent : ConversationEvent()



    //data class OnChatWithUserEvent(val conversationId:String,val message:String):ChatEvent()


}