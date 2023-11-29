package com.innov.wakasinglebase.screens.competition.joinCompetition

import CompetitionModel


data class ViewState(
    val competition:CompetitionModel?=null,
    val error:String?=null,
    val isLoading:Boolean=false,
)

data class JoinState(
    val success:Boolean=false,
    val error:String?=null,
    val isLoading:Boolean=false,
)

sealed class JoinCompetitionEvent{
    data class OnLoadCompetitionEvent(val id:String):JoinCompetitionEvent()
    data class OnSubmitJoinCompetitionEvent(val id:String):JoinCompetitionEvent()
}