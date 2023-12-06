package com.innov.wakasinglebase.screens.camera.publication

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.amplifyframework.core.Amplify
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.core.utils.FileUtils.saveBitmapToTempFile
import com.innov.wakasinglebase.domain.repository.VideoRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PublicationViewModel @Inject constructor(
    private val videoRepositoryImpl: VideoRepositoryImpl,
    @ApplicationContext val context: Context
) : BaseViewModel<ViewState, PublicationEvent>() {


    var stateFlow = MutableStateFlow(ViewState())
    val uploadState = MutableStateFlow(UploadVideoState())

    private fun publishVideoData(
        fileName: String,
        category: String,
        title: String,
        description: String,
        thumbnail: String
    ) {
        viewModelScope.launch {
            videoRepositoryImpl.createVideo(
                videoId = fileName,
                category = category,
                title = title,
                description = description,
                thumbnail = thumbnail,
            ).collect {
                when (it) {
                    is BaseResponse.Error -> {
                        stateFlow.value = stateFlow.value.copy(
                            success = false,
                            isLoading = false,
                            error = it.error
                        )
                    }

                    BaseResponse.Loading -> {
                        stateFlow.value = stateFlow.value.copy(
                            success = false,
                            isLoading = true,
                            error = null
                        )
                    }

                    is BaseResponse.Success -> {
                        stateFlow.value = stateFlow.value.copy(
                            success = true,
                            isLoading = false,
                            error = null
                        )
                    }
                }
            }
        }
    }

    private fun uploadFile(uri: Uri, fileName: String) {
        uploadState.value = uploadState.value.copy(
            success = false,
            isLoading = true,
            error = null
        )
        val stream = context.contentResolver.openInputStream(uri)
        Amplify.Storage.uploadInputStream(
            fileName,
            stream!!,
            {
                uploadState.value = uploadState.value.copy(
                    success = true,
                    //progression = 0,
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

    override fun onTriggerEvent(event: PublicationEvent) {
        when (event) {
            is PublicationEvent.OnCreateVideoEvent -> {
                publishVideoData(
                    event.fileName,
                    event.category,
                    event.title,
                    event.description,
                    event.thumbnail,
                )
            }

            is PublicationEvent.OnVideoUpload -> {
                uploadFile(event.uri, event.fileName)
            }

            is PublicationEvent.OnThumbnailUpload -> {
                val stream = saveBitmapToTempFile(context, event.bitmap!!, event.filename)
                Amplify.Storage.uploadFile(
                    event.filename,
                    stream!!,
                    {
                        println(it.key)
                    },

                    {
                        println(it.message)
                    })
            }
        }
    }
}