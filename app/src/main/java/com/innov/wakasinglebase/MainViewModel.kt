package com.innov.wakasinglebase



import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.repository.authentification.TokenRepository
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val userRepository: AuthRepositoryImpl,
    @ApplicationContext context: Context
):ViewModel() {
    private val _state = MutableStateFlow(AuthState())

    val uiState : StateFlow<AuthState> = _state.asStateFlow()
    init {
        tokenRepository.init(context)


        tokenRepository.getToken()


        viewModelScope.launch {
            userRepository.me().collect{resp->
                when(resp){
                    is BaseResponse.Error -> {
                        _state.value=_state.value.copy(
                            loading = true,
                            error = "Error of load",
                            success = false
                        )
                    }
                    is BaseResponse.Loading -> {
                        _state.value=_state.value.copy(
                            loading = true,
                            error = null,
                            success = false
                        )
                    }
                    is BaseResponse.Success -> {
                        if(resp.data==null){
                            _state.value=_state.value.copy(
                                loading = false,
                                error = "user not found",
                                success = false
                            )
                        }else{
                            _state.value=_state.value.copy(
                                loading = false,
                                error = null,
                                success = true
                            )
                        }
                    }
                    null -> {
                        _state.value=_state.value.copy(
                            loading = false,
                            error = "Unknown error",
                            success = false
                        )
                    }
                }
            }
        }


    }


}

data class AuthState(
    val success:Boolean=false,
    val error: String?=null,
    val loading:Boolean=false
)