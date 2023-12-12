package com.innov.wakasinglebase.screens.competition.vote

import CompetitionModel
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.CompetitionRepositoryImpl
import com.innov.wakasinglebase.screens.competition.joinCompetition.JoinState
import com.innov.wakasinglebase.screens.competition.watch.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VotePlayerViewModel @Inject constructor(
    private val repository: CompetitionRepositoryImpl
):BaseViewModel<ViewState,VoteCompetitionEvent>() {
    var joinState= MutableStateFlow(JoinState())
        private set


    private fun vote(id:String){
        viewModelScope.launch {
            repository.voteVideo(id = id).collect{
                when(it){
                    is BaseResponse.Error -> {
                        joinState.value=joinState.value.copy(
                            success = false,
                            isLoading = false,
                            error = it.error
                        )
                    }
                    BaseResponse.Loading -> {
                        joinState.value=joinState.value.copy(
                            success = false,
                            isLoading = true,
                            error = null
                        )
                    }
                    is BaseResponse.Success -> {
                        joinState.value=joinState.value.copy(
                            success = true,
                            isLoading = false,
                            error = null
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    private fun loadCompetition(id:String){
        viewModelScope.launch {
            repository.getCompetition(id).collect{
                when(it){
                    is BaseResponse.Error -> {
                        updateState(viewState= ViewState(
                            error = it.error,
                            isLoading = false,
                            competition = null,
                        )
                        )
                    }
                    BaseResponse.Loading -> {
                        updateState(viewState= ViewState(
                            error = null,
                            isLoading = true,
                            competition = null,
                        )
                        )
                    }
                    is BaseResponse.Success -> {
                        updateState(viewState= ViewState(
                            error = null,
                            isLoading = false,
                            competition = it.data,
                        )
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onTriggerEvent(event: VoteCompetitionEvent) {
       when(event){
           is VoteCompetitionEvent.OnLoadCompetitionEvent -> {
               loadCompetition(event.id)
           }
           is VoteCompetitionEvent.OnSubmitVoteVideoEvent -> {
               vote(event.id)
           }
       }
    }
}

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

sealed class VoteCompetitionEvent{
    data class OnLoadCompetitionEvent(val id:String):VoteCompetitionEvent()
    data class OnSubmitVoteVideoEvent(val id:String):VoteCompetitionEvent()
}