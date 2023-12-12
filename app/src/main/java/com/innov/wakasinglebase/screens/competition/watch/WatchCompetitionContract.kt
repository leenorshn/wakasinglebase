package com.innov.wakasinglebase.screens.competition.watch

import CompetitionModel
import com.innov.wakasinglebase.data.model.UserModel


data class ViewState(
    val competition:CompetitionModel?=null,
    val error:String?=null,
    val isLoading:Boolean=false,
)

data class UserState(
    val user:UserModel?=null,
    val error:String?=null,
    val isLoading:Boolean=false,
)

sealed class WatchCompetitionEvent{
    data class OnLoadCompetitionEvent(val id:String):WatchCompetitionEvent()
    object OnUserLoadedEvent:WatchCompetitionEvent()
}