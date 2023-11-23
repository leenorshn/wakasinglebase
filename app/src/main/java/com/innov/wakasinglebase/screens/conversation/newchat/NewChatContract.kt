package com.innov.wakasinglebase.screens.conversation.newchat

import com.innov.wakasinglebase.data.model.UserModel


data class ViewState(
    val users:List<UserModel>?=null,
    val isLoading:Boolean=false,
    val error:String?=null
)

data class ConversationState(
    val success:Boolean=false,
    val error:String?=null,
    val isLoading:Boolean=false
)

sealed class NewChatEvent{
    object OnLoadUsersEvent:NewChatEvent()
    data class OnStartedConversation(val userId:String):NewChatEvent()
}