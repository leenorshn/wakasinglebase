package com.innov.wakasinglebase.screens.camera.upload



import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.ui.theme.PrimaryColor
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun UploadScreen(
    navController: NavController,
    uri: String?,
    viewModel: UploadViewModel =hiltViewModel()
) {

    
    Scaffold (
        bottomBar = {
           Box(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
               Button(onClick = {
                   val uriEncoded = URLEncoder.encode(
                       uri.toString(),
                       StandardCharsets.UTF_8.toString()

                   )
                   navController.navigate(DestinationRoute.PUBLICATION_SCREEN_ROUTE.replace("{uri}",uriEncoded))
               },
                   modifier= Modifier
                       .fillMaxWidth()
                       .background(PrimaryColor)
                       .clip(RoundedCornerShape(12.dp)),) {
                   Text("continue", style = TextStyle(color = Color.White,),)
               }
           }
        }
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier= Modifier
                .padding(top = 10.dp, start = 14.dp, end = 14.dp)
                .padding(it)
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {


            Box(modifier = Modifier.weight(5f)){
                UploadVideoPlayer(video = Uri.parse(uri), onSingleTap ={} ,
                    onDoubleTap = { exoPlayer: ExoPlayer, offset: Offset -> },
                )
            }


            Spacer(modifier = Modifier.height(32.dp))


        }
    }



}
















