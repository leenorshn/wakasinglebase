package com.innov.wakasinglebase.screens.camera

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.innov.wakasinglebase.core.utils.FileUtils
import com.innov.wakasinglebase.core.utils.FileUtils.getFilePath
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.FlowPreview
//import kotlinx.coroutines.flow.internal.NopCollector.emit
import kotlinx.coroutines.launch

import video.api.client.ApiVideoClient
import video.api.client.api.ApiException
import video.api.client.api.models.VideoCreationPayload
import video.api.client.api.work.stores.VideosApiStore
import video.api.client.api.work.toProgress
import video.api.client.api.work.toVideo
import video.api.client.api.work.upload
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun UploadScreen(
    navController: NavController,
    uri: Uri,
    fileName:String,
    viewModel: UploadViewModel=hiltViewModel()
) {
   val context= LocalContext.current
    val radioOptions = listOf("Comedie", "Melodie", "Danse")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0] ) }
    var progression = remember {
        mutableStateOf(0)
    }

    val viewState by viewModel.viewState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier
            .padding(top = 40.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        val coroutineScope = rememberCoroutineScope()
        //val lifecycleOwner = LifecycleOwner()
        Text(text = "@${viewState?.currentUser?.userName}")
        Card(modifier = Modifier
            .height(380.dp,)
            .fillMaxWidth()) {
            UploadVideoPlayer(video =uri , onSingleTap ={} ,
                onDoubleTap = { exoPlayer: ExoPlayer, offset: Offset -> },
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = " $fileName", color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
       Text("Choisissez le type:",color=Color.Gray)
        radioOptions.forEach{text->
        Row(
            verticalAlignment=Alignment.CenterVertically,
           modifier= Modifier
               .fillMaxWidth()
               .selectable(
                   selected = (text == selectedOption),
                   onClick = {
                       onOptionSelected(text)
                   }
               )
               .padding(horizontal = 16.dp)
        ) {
            RadioButton(

                selected =(text==selectedOption) , onClick = { onOptionSelected(text) }
            )
            Text(text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
            }
        }
        Button(onClick = {
            //uploadVideo(uri,fileName,context)
           var workInfo= uploadVideo(context,uri,fileName, )
            if (workInfo!=null){
                workInfo.observeForever{
                   progression.value= it.progress.toProgress()
                }
            }
        }) {
            Text(text = "Upload ${progression.value}")
        }

    }
}

fun uploadVideo(context: Context, uri:Uri, fileName:String):LiveData<WorkInfo>?{
    val executor: ExecutorService = Executors.newSingleThreadExecutor()


    val apiVideoClient = ApiVideoClient("RtSnicPKEEeYc3KWVNW31UIqKhayYt1KX61gy1XZlfC")


    /**
     * This example uses an Android specific API called WorkManager to dispatch upload.
     * We initialize it before using it.
     */
    VideosApiStore.initialize(apiVideoClient.videos())
    val workManager = WorkManager.getInstance(context) // WorkManager comes from package "androidx.work:work-runtime"

    val myVideoFile = FileUtils.uriToFile2(context,uri)

    /**
     * You must not call API from the UI/main thread on Android. Dispatch with Thread, Executors,
     * Kotlin coroutines or asynchroneous API (such as `createAsync` instead of `create`).
     */
    //Log.i("Example","Started ${myVideoFile?.path}")
    if(myVideoFile!=null){
        Log.i("Error","file ${myVideoFile.path}")

        executor.execute{
            try {
                val video = apiVideoClient.videos().create(VideoCreationPayload().title(fileName))
                Log.i("Example", "Video created: $video")
               // val upload=apiVideoClient.videos().upload("${Date()}", myVideoFile)

            val operationWithRequest=   workManager.upload(video.videoId, myVideoFile)
                val workInfoliveData = workManager.getWorkInfoByIdLiveData(operationWithRequest.request.id)
                return@execute ;
//                    workInfoliveData.observeForever {
//                        it.progress.toProgress()
//                        //emit(progression)
//                    }


            //                executor.execute {
//
//
//                    workInfoliveData.observe(MainActivity@) { workInfo ->
//                        if (workInfo != null) {
//                            when (workInfo.state) {
//                                WorkInfo.State.RUNNING -> {
//                                    onUploadProgress(
//                                        operationWithRequest.request.id.toString(),
//                                        workInfo.progress.toProgress()
//                                    )
//                                }
//                                WorkInfo.State.SUCCEEDED -> {
//                                    onUploadComplete(
//                                        operationWithRequest.request.id.toString(),
//                                        workInfo.outputData.toVideo()
//                                    )
//                                }
//                                WorkInfo.State.FAILED -> {
//                                    onUploadError(
//                                        operationWithRequest.request.id.toString(),
//                                        workInfo.outputData.getString(AbstractUploadWorker.ERROR_KEY) ?: "Unknown error"
//                                    )
//                                }
//                                WorkInfo.State.CANCELLED -> {
//                                    onUploadCancelled(operationWithRequest.request.id.toString())
//                                }
//                                else -> {
//                                    // Do nothing
//                                }
//                            }
//                        }
//                    }
 //               }

            } catch (e: ApiException) {
                Log.e("Example", "Exception when calling VideoApi", e)
                return@execute
            }

        }
    }
return null
}










