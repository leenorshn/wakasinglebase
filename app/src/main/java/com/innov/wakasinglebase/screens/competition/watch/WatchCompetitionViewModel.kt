package com.innov.wakasinglebase.screens.competition.watch

import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.data.repository.authentification.AuthRepository
import com.innov.wakasinglebase.domain.CompetitionRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class WatchCompetitionViewModel @Inject constructor(
    private val repositoryAuth: AuthRepository,
    private val repository: CompetitionRepositoryImpl
):BaseViewModel<ViewState,WatchCompetitionEvent>(){

    val userState = MutableStateFlow(UserState())

    init {
        getCurrentUser()
    }
    override fun onTriggerEvent(event: WatchCompetitionEvent) {
        when(event){
            is WatchCompetitionEvent.OnLoadCompetitionEvent -> {
                loadCompetition(event.id)
            }

            else -> {}
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

    private fun getCurrentUser() {
        viewModelScope.launch {
            repositoryAuth.me().collect {result->
                when(result){
                    is BaseResponse.Loading->{
                        userState.value = userState.value.copy(
                            isLoading = true
                        )
                    }
                    is BaseResponse.Success->{
                        userState.value = userState.value.copy(
                            isLoading = false, user = result.data
                        )
                    }
                    is BaseResponse.Error->{
                        userState.value = userState.value.copy(
                            isLoading = false,
                            user = null,
                            error = result.error
                        )
                    }
                }


            }
        }
    }
}