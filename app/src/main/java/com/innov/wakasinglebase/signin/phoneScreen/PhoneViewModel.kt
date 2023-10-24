package com.innov.wakasinglebase.signin.phoneScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class PhoneViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
):ViewModel() {
    var uiState= mutableStateOf(PhoneState())
    var _verifyState=MutableStateFlow(VerificationState())
    val verificationState=_verifyState.asStateFlow()

    fun onEvent(event: PhoneEvent){
        when(event){
            is PhoneEvent.OnPhoneEntered -> {
                uiState.value=uiState.value.copy(
                    phone = event.phone
                )
            }
            PhoneEvent.OnSubmit->{
                uiState.value=uiState.value.copy(
                    showDialog = true
                )

            }
            PhoneEvent.HideDialog -> {
                uiState.value=uiState.value.copy(
                    showDialog = false
                )
            }

            PhoneEvent.OnSubmitVerification -> {
                viewModelScope.launch {
                    repository.verifyPhone(uiState.value.phone).collect{
                        when(it){
                            is BaseResponse.Error -> {
                                _verifyState.value=_verifyState.value.copy(
                                    success = false,
                                    error = "${it.error} "
                                )
                                uiState.value=uiState.value.copy(
                                    showDialog = false
                                )
                            }
                            BaseResponse.Loading -> {
                                _verifyState.value=_verifyState.value.copy(
                                    success = false,
                                    isLoading = true
                                )
                            }
                            is BaseResponse.Success -> {
                                _verifyState.value=_verifyState.value.copy(
                                    success = true,
                                    isLoading = false
                                )
                                uiState.value=uiState.value.copy(
                                    showDialog = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun isValid():Boolean{
        return uiState.value.phone.length==13
    }

}

data class VerificationState(
    val isLoading:Boolean=false,
    val success:Boolean=false,
    val error: String?=null
)

data class PhoneState(
    val phone:String="",
    val showDialog:Boolean=false
)

sealed class PhoneEvent(){
    data class OnPhoneEntered(val phone:String):PhoneEvent()
    object OnSubmit:PhoneEvent()

    object OnSubmitVerification:PhoneEvent()
    object HideDialog:PhoneEvent()
}