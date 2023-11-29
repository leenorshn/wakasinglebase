package com.innov.wakasinglebase.screens.competition

import CompetitionModel
import com.innov.wakasinglebase.data.model.UserModel

data class ViewState(
    val competitions: List<CompetitionModel>? = null,
    val error:String?=null,
    val isLoading:Boolean=false
)


data class CompetitionState(
    val success:Boolean=false,
    val error:String?=null,
    val isLoading:Boolean=false
)
data class UserState(
    val user:UserModel?=null,
    val error:String?=null,
    val isLoading:Boolean=false
)

sealed class ConversationEvent {
    object EventFetchUserEvent : ConversationEvent()



    //data class OnChatWithUserEvent(val conversationId:String,val message:String):ChatEvent()


}