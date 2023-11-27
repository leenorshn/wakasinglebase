package com.innov.wakasinglebase.screens.competition


import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.CompetitionRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitionViewModel @Inject constructor(
    private val competitionRepository: CompetitionRepositoryImpl,
) : BaseViewModel<ViewState, ConversationEvent>() {


    init {

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

}