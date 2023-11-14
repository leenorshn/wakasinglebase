package com.innov.wakasinglebase.screens.myprofil.edit_profile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.core.Amplify
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.repository.authentification.TokenRepository
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    var uploadState = MutableStateFlow(UploadImageState())
    private set

    var name by mutableStateOf("")
    var bio by mutableStateOf("")


    init {
        tokenRepository.init(context)
        tokenRepository.getToken()
       loadUser()
    }

   private fun loadUser(){
        viewModelScope.launch {
            repository.me().collect{
                when(it){
                    is BaseResponse.Error -> {
                        _state.value=_state.value.copy(
                            loadUserError = "${it.error}",
                            userModel = null,
                            userIsLoading = false,
                        )
                    }
                    BaseResponse.Loading -> {
                        _state.value=_state.value.copy(
                            userIsLoading = true,
                            loadUserError = null,
                            userModel = null,
                        )
                    }
                    is BaseResponse.Success -> {
                        _state.value=_state.value.copy(
                            userModel = it.data,
                            userIsLoading = false,
                            loadUserError = null,
                        )

                        name=it.data?.name?:""
                    }
                }
            }
        }
    }

    fun onEvent(event: EditProfileEvent){
        when(event){
            is EditProfileEvent.OnNameEntered -> {
                Log.d("VICTOR",event.name)
                _state.value=_state.value.copy(
                    name =  event.name,
                )
            }
            EditProfileEvent.OnSubmit -> {

                viewModelScope.launch {
                    updateUserData(_state.value)
                }

            }

            is EditProfileEvent.OnAvatarEntered -> {
                //val avatar="https://d2y4y6koqmb0v7.cloudfront.net/${event.avatar}"
                _state.value=_state.value.copy(
                    avatar =  event.avatar,
                )
            }

            EditProfileEvent.ReloadUser -> {
                loadUser()
            }

            is EditProfileEvent.OnUploadImageOns3 -> {
                Log.d("IMAGE_UPLOAD",event.fileName)
                Log.d("IMAGE_UPLOAD",event.uri.toString())
                uploadFile(event.uri,event.fileName)
            }

            is EditProfileEvent.OnBioEntered -> {
                _state.value=_state.value.copy(
                    bio =  event.bio,
                )
            }
        }
    }

    fun isValid():Boolean{
        return !_state.value.name.isNullOrEmpty() && !_state.value.avatar.isNullOrEmpty()
    }

   private fun uploadFile(uri: Uri,fileName: String){
        //val options = StorageUploadInputStreamOptions.defaultInstance()
        val stream = context.contentResolver.openInputStream(uri)
       uploadState.value=uploadState.value.copy(
           success = false,
           isLoading = true,
           error=null
       )
        Amplify.Storage.uploadInputStream(
            "$fileName",
            stream!!,
            {
                uploadState.value=uploadState.value.copy(
                    success = true,
                    isLoading = false,
                    error=null
                )
            },

            {
                uploadState.value=uploadState.value.copy(
                    success = false,
                    isLoading = false,
                    error=it.message
                )
            })
    }



    private suspend fun updateUserData(data: FirstProfileState) = withContext(Dispatchers.IO){

      viewModelScope.launch {
          repository.updateUserData(name=data.name, avatar = data.avatar,bio=data.bio).collect{
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





}

data class UpdateUserState(
    val isLoading:Boolean=false,
    val success:Boolean=false,
    val error:String?=null
)



data class FirstProfileState(
    var name:String?=null,
    val avatar:String?=null,
    val bio:String?=null,
    val userModel: UserModel?=null,
    val loadUserError:String?=null,
    val userIsLoading:Boolean=false
)

data class UploadImageState(
    val success: Boolean=false,
    val isLoading: Boolean=false,
    val error: String?=null,
)

sealed class EditProfileEvent{
    data class OnNameEntered(val name:String): EditProfileEvent()

    data class OnBioEntered(val bio: String):EditProfileEvent()

    data class OnAvatarEntered(val avatar: String):EditProfileEvent()

    data class OnUploadImageOns3(val uri:Uri,val fileName:String):EditProfileEvent()

    object ReloadUser:EditProfileEvent()

    object OnSubmit: EditProfileEvent()

}