package com.innov.wakasinglebase.screens.home.foryou

import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.repository.VideoRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by innov Victor on 3/15/2023.
 */
@HiltViewModel
class ForYouViewModel @Inject constructor(
    private val videoRepository: VideoRepositoryImpl
) : BaseViewModel<ViewState, ForYouEvent>() {
    init {
        viewModelScope.launch {
            getForYouPageFeeds()
        }
    }

    override fun onTriggerEvent(event: ForYouEvent) {
        when(event){
            ForYouEvent.OnLoadVideo -> {
                viewModelScope.launch {
                    getForYouPageFeeds()
                }
            }
        }
    }

    private suspend fun getForYouPageFeeds() = withContext(Dispatchers.IO){
        viewModelScope.launch {
            videoRepository.getAllVideos().collect{
                when(it){
                    is BaseResponse.Error -> {
                        updateState(ViewState(
                            error = "Loading error"
                        ))
                    }
                    BaseResponse.Loading -> {
                        updateState(ViewState(
                            isLoading = true
                        ))
                    }
                    is BaseResponse.Success -> {
                        updateState(ViewState(
                            forYouPageFeed = it.data,
                            isLoading = false
                        ))
                    }
                }
            }
        }
    }


}