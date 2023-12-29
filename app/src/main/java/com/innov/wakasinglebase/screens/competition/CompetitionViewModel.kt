package com.innov.wakasinglebase.screens.competition


import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.CompetitionRepositoryImpl
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitionViewModel @Inject constructor(
    private val competitionRepository: CompetitionRepositoryImpl,
    private val repositoryImpl: AuthRepositoryImpl,
) : BaseViewModel<ViewState, ConversationEvent>() {

val uiState = MutableStateFlow(UserState())
    init {
       // getSignedInUser()
        getCompetitions()
    }

    override fun onTriggerEvent(event: ConversationEvent) {
        when (event) {
            ConversationEvent.EventFetchUserEvent -> {
                getCompetitions()

            }

        }
    }


    private  fun getCompetitions()  {
        viewModelScope.launch {
            competitionRepository.getCompetitions().collect {
                when (it) {
                    is BaseResponse.Error -> {

                       updateState(
                           viewState= ViewState(
                               competitions = null,
                               isLoading = false,
                               error = it.error
                           )
                       )
                    }

                    BaseResponse.Loading -> {
                        updateState(
                            viewState=ViewState(
                                competitions = null,
                                isLoading = true,
                                error = null
                            )
                        )
                    }

                    is BaseResponse.Success -> {
                        updateState(
                            viewState=ViewState(
                                competitions = it.data,
                                isLoading = false,
                                error = null
                            )
                        )

                    }
                }
            }
        }
    }

    private fun getSignedInUser() {
        viewModelScope.launch {
            repositoryImpl.me().collect {result->
                when(result){
                    is BaseResponse.Loading->{
                        uiState.value = uiState.value.copy(
                            isLoading = true,
                            user = null,
                            error = null
                        )
                    }
                    is BaseResponse.Success->{
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            user = result.data,
                            error = null,
                        )
                    }
                    is BaseResponse.Error->{
                        uiState.value = uiState.value.copy(
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