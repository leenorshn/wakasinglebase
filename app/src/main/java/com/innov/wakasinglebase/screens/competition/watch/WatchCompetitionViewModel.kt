package com.innov.wakasinglebase.screens.competition.watch

import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.CompetitionRepositoryImpl
import com.innov.wakasinglebase.screens.competition.joinCompetition.JoinState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class WatchCompetitionViewModel @Inject constructor(
    private val repository: CompetitionRepositoryImpl
):BaseViewModel<ViewState,WatchCompetitionEvent>(){

    var joinState= MutableStateFlow(JoinState())
        private set
    override fun onTriggerEvent(event: WatchCompetitionEvent) {
        when(event){
            is WatchCompetitionEvent.OnLoadCompetitionEvent -> {
                loadCompetition(event.id)
            }
            is WatchCompetitionEvent.OnSubmitVoteVideoEvent -> {
                vote(id = event.id)
            }
        }
    }

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
                }
            }
        }
    }
}