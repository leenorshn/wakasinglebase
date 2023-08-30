package com.innov.wakasinglebase.screens.camera.upload



import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.signin.utils.UploadVideoOnS3
import com.innov.wakasinglebase.ui.theme.PrimaryColor
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun UploadScreen(
    navController: NavController,
    uri: String?,
    viewModel: UploadViewModel =hiltViewModel()
) {
   val context= LocalContext.current
//    val radioOptions = listOf("Comedie", "Melodie", "Danse")
//    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0] ) }
//
//    var fileName= getFileNameFromUri(context,Uri.parse(uri))
    
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
//        viewModel.uiState.value.currentUser.let {
//            Text(text = "@${it?.name}")
//        }


            Box(modifier = Modifier.weight(5f)){
                UploadVideoPlayer(video = Uri.parse(uri), onSingleTap ={} ,
                    onDoubleTap = { exoPlayer: ExoPlayer, offset: Offset -> },
                )
            }




//        Spacer(modifier = Modifier.height(8.dp))
//        Text(text = " $fileName", color = Color.Black)
//        Spacer(modifier = Modifier.height(8.dp))
//       Text("Choisissez le type:",color=Color.Gray)
//        radioOptions.forEach{text->
//        Row(
//            verticalAlignment=Alignment.CenterVertically,
//           modifier= Modifier
//               .fillMaxWidth()
//               .selectable(
//                   selected = (text == selectedOption),
//                   onClick = {
//                       onOptionSelected(text)
//                   }
//               )
//               .padding(horizontal = 16.dp)
//        ) {
//            RadioButton(
//
//                selected =(text==selectedOption) , onClick = { onOptionSelected(text) }
//            )
//            Text(text = text,
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier.padding(start = 16.dp)
//            )
//            }
//        }
//        viewModel.uiState.value.currentUser?.let {user->
////
//                    UploadVideoOnS3(context = context, videoUri = Uri.parse(uri),
//                        videoKey = "$fileName",
//                        user = user,
//                        description = selectedOption,
//                        navController=navController
//                    )
//        }

            Spacer(modifier = Modifier.height(32.dp))


        }
    }



}
















