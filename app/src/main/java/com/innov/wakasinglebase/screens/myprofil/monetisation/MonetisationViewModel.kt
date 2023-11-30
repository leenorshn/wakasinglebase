package com.innov.wakasinglebase.screens.myprofil.monetisation

import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MonetisationViewModel @Inject constructor(
private val repository: AuthRepositoryImpl
):BaseViewModel<ViewState,MonetisationEvent>() {
    override fun onTriggerEvent(event: MonetisationEvent) {
        when(event){
            MonetisationEvent.OnMonetisationSend -> {
                askMonetisation()
            }
            else -> {}
        }
    }

    private fun askMonetisation(){
        viewModelScope.launch {
            repository.askMonetisation().collect{
                when(it){
                    is BaseResponse.Error -> {
                        updateState(viewState= ViewState(
                            error = it.error,
                            isLoading = false,
                            success = false,
                        )
                        )
                    }
                    BaseResponse.Loading -> {
                        updateState(viewState=ViewState(
                            error = null,
                            isLoading = true,
                            success = false,
                        ))
                    }
                    is BaseResponse.Success -> {
                        updateState(viewState=ViewState(
                            error = null,
                            isLoading = false,
                            success = true,
                        ))
                    }
                }
            }
        }
    }
}