package com.innov.wakasinglebase.screens.market


import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.ticket.TicketRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val repository: TicketRepositoryImpl
) : BaseViewModel<ViewState, MarketMediaEvent>() {

    init {
        getTickets()
    }

    override fun onTriggerEvent(event: MarketMediaEvent) {
        when (event) {
            MarketMediaEvent.EventFetchTemplate -> getTickets()
        }
    }

    private fun getTickets() {
        viewModelScope.launch {
            repository.getAllTicket().collect {
                when(it){
                    is BaseResponse.Error -> {

                        updateState((viewState.value ?: ViewState()).copy(error = it.error, isLoading = false))
                    }
                    BaseResponse.Loading -> {
                        updateState((viewState.value ?: ViewState()).copy(isLoading = true, error = null))
                    }
                    is BaseResponse.Success -> {
                        updateState((viewState.value ?: ViewState()).copy(tickets  = it.data,
                            isLoading = false, error = null))
                    }
                }
            }
        }
    }


}