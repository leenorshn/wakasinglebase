package com.innov.wakasinglebase.signin.name_and_avatar

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.core.Amplify
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.repository.authentification.TokenRepository
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FirstProfileViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl,
    private val tokenRepository: TokenRepository,
    @ApplicationContext val context: Context,

    ):ViewModel() {


   private var _state= MutableStateFlow(FirstProfileState())
    val state=_state.asStateFlow()

    var updateUserState= MutableStateFlow(UpdateUserState())

    init {
        tokenRepository.init(context)
        tokenRepository.getToken()
        viewModelScope.launch {
            repository.me().collect{
                when(it){
                    is BaseResponse.Error -> {
                        _state.value=_state.value.copy(
                            loadUserError = "${it.error}"
                        )
                    }
                    BaseResponse.Loading -> {
                        _state.value=_state.value.copy(
                           userIsLoading = true
                        )
                    }
                    is BaseResponse.Success -> {
                        _state.value=_state.value.copy(
                            userModel = it.data
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: FirstProfileEvent){
        when(event){
            is FirstProfileEvent.OnAvatarChanged -> {
                _state.value=_state.value.copy(
                    avatar = event.uri,
                    fileName = event.fileName
                )
            }
            is FirstProfileEvent.OnNameEntered -> {
                _state.value=_state.value.copy(
                    name = event.name,
                )
            }
            FirstProfileEvent.OnSubmit -> {
                updateUserData(_state.value)
            }

            FirstProfileEvent.OnImageUpload -> {
                _state.value.avatar?.let { uploadImage(it,_state.value.fileName) }
            }
        }
    }

    private fun updateUserData(data:FirstProfileState){

      viewModelScope.launch {
          repository.updateUserData(data.name,data.fileName).collect{
              when(it){
                  is BaseResponse.Error -> {
                      updateUserState.value=updateUserState.value.copy(
                          error = it.error,
                          isLoading = false
                      )
                  }
                  BaseResponse.Loading -> {
                      updateUserState.value=updateUserState.value.copy(
                          isLoading = true,
                          error = null
                      )
                  }
                  is BaseResponse.Success -> {
                      updateUserState.value=updateUserState.value.copy(
                        success = true,
                          isLoading = false,
                          error=null
                      )
                  }
              }
          }
      }
    }



    private fun uploadImage(uri: Uri,fileName: String){
        val stream = context.contentResolver.openInputStream(uri)

        Amplify.Storage.uploadInputStream(
            fileName,
            stream!!,
            {
                //isLoading = false
                _state.value=_state.value.copy(
                    isUploaded = true
                )
            },

            {
               // isLoading = false
                _state.value=_state.value.copy(
                    isUploaded = false,
                    uploadError = it.message
                )
            })
    }

}

data class UpdateUserState(
    val isLoading:Boolean=false,
    val success:Boolean=false,
    val error:String?=null
)

data class FirstProfileState(
    val name:String="",
    val avatar:Uri?=null,
    val userModel: UserModel?=null,
    val fileName: String="",
    val isUploaded:Boolean=false,
    val uploadError:String?=null,
    val loadUserError:String?=null,
    val userIsLoading:Boolean=false
)

sealed class FirstProfileEvent{
    data class OnNameEntered(val name:String):FirstProfileEvent()
    data class OnAvatarChanged(val uri: Uri,val fileName:String):FirstProfileEvent()
    object OnSubmit:FirstProfileEvent()
    object OnImageUpload:FirstProfileEvent()
}