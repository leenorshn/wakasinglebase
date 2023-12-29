package com.innov.wakasinglebase



import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.data.model.UserModel
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
):BaseViewModel<ViewState,MainEvent>() {
    private val _state = MutableStateFlow(AuthState())

    val uiState : StateFlow<AuthState> = _state.asStateFlow()
    init {
        loadUser(context)
    }

   private fun loadUser(context:Context){
        tokenRepository.init(context)


        tokenRepository.getToken()


        viewModelScope.launch {
            userRepository.me().collect{resp->
                when(resp){
                    is BaseResponse.Error -> {
                        Log.e("AUTH_STATE",resp.error)
                        _state.value=_state.value.copy(
                            loading = false,
                            error = "Error of load",
                            success = false
                        )
                    }
                    is BaseResponse.Loading -> {
                        Log.e("AUTH_STATE","Isloading ....")
                        _state.value=_state.value.copy(
                            loading = true,
                            error = null,
                            success = false
                        )
                    }
                    is BaseResponse.Success -> {
                        if(resp.data==null){
                            Log.e("AUTH_STATE","user not found")
                            _state.value=_state.value.copy(
                                loading = false,
                                error = "user not found",
                                success = false
                            )
                        }else{
                            Log.e("AUTH_STATE","done")
                            _state.value=_state.value.copy(
                                loading = false,
                                error = null,
                                success = true
                            )
                        }
                    }
                    null -> {
                        Log.e("AUTH_STATE","Unknown error")
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

    override fun onTriggerEvent(event: MainEvent) {
        when(event){
           is MainEvent.OnReloadUser -> {
                loadUser(context = event.context)
            }
        }
    }


}

data class AuthState(
    val success:Boolean=false,
    val error: String?=null,
    val loading:Boolean=false
)

data class ViewState(
    val success: UserModel
)

sealed class MainEvent{
    data class OnReloadUser(val context: Context):MainEvent()

}