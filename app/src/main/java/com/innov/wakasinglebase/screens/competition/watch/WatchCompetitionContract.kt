package com.innov.wakasinglebase.screens.competition.watch

import CompetitionModel


data class ViewState(
    val competition:CompetitionModel?=null,
    val error:String?=null,
    val isLoading:Boolean=false,
)

data class VoteState(
    val success:Boolean=false,
    val error:String?=null,
    val isLoading:Boolean=false,
)

sealed class WatchCompetitionEvent{
    data class OnLoadCompetitionEvent(val id:String):WatchCompetitionEvent()
    data class OnSubmitVoteVideoEvent(val id:String):WatchCompetitionEvent()
}