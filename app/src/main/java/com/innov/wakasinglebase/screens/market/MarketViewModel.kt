package com.innov.wakasinglebase.screens.market


import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.ticket.EventRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val repository: EventRepositoryImpl
) : BaseViewModel<ViewState, MarketMediaEvent>() {

    init {
        viewModelScope.launch {
            getTickets()
        }
    }

    override fun onTriggerEvent(event: MarketMediaEvent) {
        when (event) {
            MarketMediaEvent.EventFetchTemplate -> {
                viewModelScope.launch {
                    getTickets()
                }
            }
        }
    }

    private suspend fun getTickets() = withContext(Dispatchers.IO) {
        viewModelScope.launch {
            repository.getAllEvents().collect {
                when (it) {
                    is BaseResponse.Error -> {

                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                error = it.error,
                                isLoading = false,
                                tickets = null
                            )
                        )
                    }

                    BaseResponse.Loading -> {
                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                isLoading = true,
                                error = null,
                                tickets = null
                            )
                        )
                    }

                    is BaseResponse.Success -> {
                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                tickets = it.data,
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