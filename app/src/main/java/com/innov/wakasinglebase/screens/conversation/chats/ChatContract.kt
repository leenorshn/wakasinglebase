package com.innov.wakasinglebase.screens.conversation.chats

import com.innov.wakasinglebase.data.model.ChatModel


data class ViewState(
    val chats:List<ChatModel>?=null,
    val isLoading:Boolean=false,
    val error:String?=null,
)

sealed class ChatEvent{
    data class OnSendMessage(val conversationId:String,val message:String):ChatEvent()

}