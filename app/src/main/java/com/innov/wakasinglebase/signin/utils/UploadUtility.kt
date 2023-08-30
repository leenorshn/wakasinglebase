package com.innov.wakasinglebase.signin.utils

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.amplifyframework.core.Amplify
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.innov.wakasinglebase.core.DestinationRoute.HOME_SCREEN_ROUTE
import com.innov.wakasinglebase.data.model.UserModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun UploadVideoOnS3(context: Context,
                    videoUri: Uri,
                    videoKey:String,
                    user:UserModel,
                    description:String,
                    navController: NavController
){

    var message by remember {
        mutableStateOf("Upload")
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
val scope= rememberCoroutineScope()

    Button(onClick = {
        val stream=  context.contentResolver.openInputStream(videoUri)
        isLoading=true
        Amplify.Storage.uploadInputStream(
            videoKey,
            stream!!,
            {

                scope.launch{
                    createVideo(
                        videoId = videoKey,
                        author =user,
                        description = description,
                    )
                }
                isLoading=false
                message=  "done"
                navController.navigate(HOME_SCREEN_ROUTE)

            },

            {
                isLoading=false
                message=  "try again"
            }
        )

    },
        enabled = !isLoading

    ) {
        Text(text = "$message ")
        if (isLoading){
            Spacer(modifier = Modifier.width(8.dp))
            CircularProgressIndicator()
        }

    }

}

 suspend fun createVideo(videoId: String, author: UserModel,description:String): Result<String> {
    val db=FirebaseFirestore.getInstance()
    val videoUp= mapOf(
        "videoId" to videoId,
        "videoLink" to "https://d2y4y6koqmb0v7.cloudfront.net/$videoId",
        "description" to "$description ",
        "like" to 0,
        "share" to 0,
        "authorDetails" to author.uid,
        "createdAt" to Timestamp.now().seconds
    )
    return try {
        db.collection("videos").add(videoUp).await()
        return Result.success("Done")
    }catch (ex:Exception){
        return Result.failure(ex)
    }
}


