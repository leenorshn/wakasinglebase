package com.innov.wakasinglebase.signin.profile_setting

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.amplifyframework.core.Amplify
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
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
class ProfileSettingViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl,
    private val tokenRepository: TokenRepository,

    @ApplicationContext val context: Context,
) : BaseViewModel<ViewState, ProfileSettingEvent>() {

    private var _state = MutableStateFlow(ProfileSettingState())
    val state = _state.asStateFlow()

    var updateUserState = MutableStateFlow(UpdateUserState())
    var uploadState = MutableStateFlow(UploadImageState2())
        private set

    var name by mutableStateOf("")


    init {
        initLoad()
    }

    private fun initLoad(){
        tokenRepository.init(context)
        tokenRepository.getToken()
        loadUser()
    }


    override fun onTriggerEvent(event: ProfileSettingEvent) {
        when (event) {
            is ProfileSettingEvent.OnNameEntered -> {
                Log.d("VICTOR", event.name)
                _state.value = _state.value.copy(
                    name = event.name,
                )
            }

            ProfileSettingEvent.OnSubmit -> {

                viewModelScope.launch {
                    updateUserData(_state.value)
                }

            }

            is ProfileSettingEvent.OnAvatarEntered -> {
                //val avatar="https://d2y4y6koqmb0v7.cloudfront.net/${event.avatar}"
                _state.value = _state.value.copy(
                    avatar = event.avatar,
                )
            }

            ProfileSettingEvent.ReloadUser -> {
                initLoad()
            }

            is ProfileSettingEvent.OnUploadImageOns3 -> {
                Log.d("IMAGE_UPLOAD", event.fileName)
                Log.d("IMAGE_UPLOAD", event.uri.toString())
                uploadFile(event.uri, event.fileName)
            }


        }

    }

    private fun loadUser() {
        viewModelScope.launch {
            repository.me().collect {
                when (it) {
                    is BaseResponse.Error -> {
                        updateState(
                            viewState = ViewState(
                                isLoading = false,
                                userModel = null,
                                error = it.error
                            )
                        )

                    }

                    BaseResponse.Loading -> {
                        updateState(
                            viewState = ViewState(
                                isLoading = true,
                                userModel = null,
                                error = null
                            )
                        )
                    }

                    is BaseResponse.Success -> {


                        updateState(
                            viewState = ViewState(
                                isLoading = false,
                                userModel = it.data,
                                error = null
                            )
                        )
                        name = it.data?.name ?: ""
                    }
                }
            }
        }
    }

    private fun uploadFile(uri: Uri, fileName: String) {
        //val options = StorageUploadInputStreamOptions.defaultInstance()
        val stream = context.contentResolver.openInputStream(uri)
        uploadState.value = uploadState.value.copy(
            success = false,
            isLoading = true,
            error = null
        )
        Amplify.Storage.uploadInputStream(
            "$fileName",
            stream!!,
            {
                uploadState.value = uploadState.value.copy(
                    success = true,
                    isLoading = false,
                    error = null
                )
            },

            {
                uploadState.value = uploadState.value.copy(
                    success = false,
                    isLoading = false,
                    error = it.message
                )
            })
    }

    fun isValid(): Boolean {
        return (_state.value.name?.isNotEmpty() == true) && (_state.value.avatar?.isNotEmpty() == true)
    }

    private suspend fun updateUserData(data: ProfileSettingState) = withContext(Dispatchers.IO) {

        viewModelScope.launch {
            repository.updateUserData(name = data.name, avatar = data.avatar, bio = "not define")
                .collect {
                    when (it) {
                        is BaseResponse.Error -> {
                            updateUserState.value = updateUserState.value.copy(
                                error = it.error,
                                isLoading = false
                            )
                        }

                        BaseResponse.Loading -> {
                            updateUserState.value = updateUserState.value.copy(
                                isLoading = true,
                                error = null
                            )
                        }

                        is BaseResponse.Success -> {
                            updateUserState.value = updateUserState.value.copy(
                                success = true,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                }
        }
    }


}