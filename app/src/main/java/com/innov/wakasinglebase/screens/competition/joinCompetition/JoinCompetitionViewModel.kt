package com.innov.wakasinglebase.screens.competition.joinCompetition

import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.CompetitionRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class JoinCompetitionViewModel @Inject constructor(
    private val repository: CompetitionRepositoryImpl
):BaseViewModel<ViewState,JoinCompetitionEvent>() {

    var joinState= MutableStateFlow(JoinState())
    private set
    override fun onTriggerEvent(event: JoinCompetitionEvent) {
        when(event){
            is JoinCompetitionEvent.OnLoadCompetitionEvent -> {
                loadCompetition(event.id)
            }
            is JoinCompetitionEvent.OnSubmitJoinCompetitionEvent -> {
                join(event.id)
            }
        }
    }

    private fun join(id:String){
        viewModelScope.launch {
            repository.joinCompetition(uid = id).collect{
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
                            error=it.error,
                            isLoading = false,
                            competition = null,
                        )
                        )
                    }
                    BaseResponse.Loading -> {
                        updateState(viewState= ViewState(
                            error=null,
                            isLoading = true,
                            competition = null,
                        )
                        )
                    }
                    is BaseResponse.Success -> {
                        updateState(viewState= ViewState(
                            error=null,
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