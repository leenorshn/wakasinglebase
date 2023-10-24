package com.innov.wakasinglebase.signin.opt_screen

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.repository.authentification.TokenRepository
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class OptViewModel @Inject constructor(
    private val repository:AuthRepositoryImpl,
    private val tokenRepository:TokenRepository,
    @ApplicationContext context: Context
):ViewModel(

) {

    init {
        tokenRepository.init(context)
       // tokenRepository.getToken()
    }

    var uiState= mutableStateOf(OptState())

    fun onEvent(event: CodeEvent){
        when(event){
           is CodeEvent.OnCodeEntered -> {
                uiState.value=uiState.value.copy(
                    code = event.code
                )
            }

            is CodeEvent.OnSubmit ->{
                viewModelScope.launch {
                    uiState.value=uiState.value.copy(
                        isLoading = true
                    )
                    repository.verifyCode(phone = event.phone,uiState.value.code).collect{
                        when(it){
                            is BaseResponse.Error -> {
                                uiState.value=uiState.value.copy(
                                    isLoading = false,
                                    error = it.error
                                )
                            }
                            BaseResponse.Loading -> {
                                uiState.value=uiState.value.copy(
                                    isLoading = true
                                )
                            }
                            is BaseResponse.Success -> {
                                tokenRepository.setToken("${it.data?.token}")
                                uiState.value=uiState.value.copy(
                                    isLoading = false,
                                    success = true
                                )
                            }
                        }
                    }

                }
            }
        }
    }

    fun isValid():Boolean{
        return uiState.value.code.length==6
    }
}

data class OptState(
    val isLoading:Boolean=false,
    val success:Boolean=false,
    val error:String?=null,
    val code:String="",
)



sealed class CodeEvent(){
    data class OnCodeEntered(val code:String):CodeEvent()
    data class OnSubmit(val phone:String):CodeEvent()
}