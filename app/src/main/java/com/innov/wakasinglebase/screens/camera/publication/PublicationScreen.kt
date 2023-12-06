package com.innov.wakasinglebase.screens.camera.publication

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.common.TopBar
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.core.extension.LargeSpace
import com.innov.wakasinglebase.core.extension.MediumSpace
import com.innov.wakasinglebase.core.extension.SmallSpace
import com.innov.wakasinglebase.core.utils.FileUtils
import com.innov.wakasinglebase.core.utils.FileUtils.getFileNameFromUri
import com.innov.wakasinglebase.ui.theme.Gray
import com.innov.wakasinglebase.ui.theme.PrimaryColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

@Composable
fun PublicationScreen(
    navController: NavController,
    uri: Uri?,
    viewModel: PublicationViewModel = hiltViewModel()
) {

    val uiState by viewModel.stateFlow.collectAsState()
    val uploadVideoState by viewModel.uploadState.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var thumbnail by remember {
        mutableStateOf<Pair<Bitmap?, Boolean>>(Pair(null, true))  //bitmap, isShow
    }

    val fileName = getFileNameFromUri(context, uri!!)




    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
            val bm = FileUtils.extractThumbnailFromUri(
                context,
                uri
            )
            withContext(Dispatchers.Main) {
                thumbnail = thumbnail.copy(first = bm, second = thumbnail.second)
                viewModel.onTriggerEvent(PublicationEvent.OnThumbnailUpload(thumbnail.first,"thumbnail_${fileName?.replace(".mp4",".jpg")}"))
            }

        }
        fileName?.let { PublicationEvent.OnVideoUpload(uri, it) }
            ?.let { viewModel.onTriggerEvent(it) }
    }

    LaunchedEffect(key1 = uiState.success){
        if (uiState.success){
            // todo show toast
            navController.navigate(DestinationRoute.HOME_SCREEN_ROUTE)
        }
    }

    var description by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }


    val radioOptions = listOf("PUB", "NORMAL", "CHALLENGE", "Other")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1]) }
    Scaffold(
        topBar = {
            TopBar(
                title = "Publication",

                onClickNavIcon = {
                    navController.navigateUp()
                },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Column {
                Text("Video upload", color = Gray)
                Spacer(modifier = Modifier.height(5.dp))
                if (thumbnail.second) {
                    Card(
                        modifier = Modifier
                            // .background(PrimaryColor)
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                            .background(
                                if (uploadVideoState.isLoading) Color.Yellow else if(uploadVideoState.error!=null) Color.Red else Color(
                                    0xFF21CE99
                                )
                            )

                            .padding(4.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            )


                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AsyncImage(
                                model = thumbnail.first,
                                contentDescription = "image preview",
                                modifier = Modifier.size(180.dp)
                            )
                            LoadingIndicator(uploadVideoState.isLoading)
                        }
                    }
                }
                SmallSpace()
                Text(text = "Details", color = Gray)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    placeholder = {
                        Text("video title here")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = title.isEmpty(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,

                        )

                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { value ->
                        description = value
                    },
                    label = { Text(text = "Description") },
                    minLines = 1,
                    maxLines = 4,
                    placeholder = {
                        Text("Tape video summary here")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = title.isEmpty(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,

                        )
                )
                SmallSpace()
                Text(text = "Categorie de la video", color = Gray)
                Column {
                    radioOptions.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = {
                                        onOptionSelected(text)
                                    }
                                )
                                .padding(horizontal = 6.dp),
                            // horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = PrimaryColor,
                                    unselectedColor = Color.Black,
                                )
                            )
                            Text(
                                text = text.lowercase(Locale.ROOT),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )

                        }
                    }
                }
                LargeSpace()
                if(uploadVideoState.error!=null){
                    Text(text = "${uploadVideoState.error}", color = Color.Red)
                    MediumSpace()
                }
               if (uploadVideoState.error==null ){
                   if (!uploadVideoState.isLoading) {
                       CustomButton(
                           buttonText = if (uiState.isLoading) "wait ..." else "Publish",
                           containerColor = PrimaryColor,
                           shape = RoundedCornerShape(24.dp),
                           modifier = Modifier.fillMaxWidth(),
                           isEnabled = !uiState.isLoading
                       ) {

                           viewModel.onTriggerEvent(
                               PublicationEvent.OnCreateVideoEvent(
                                   fileName = fileName!!,
                                   category = selectedOption,
                                   title = title,
                                   thumbnail = "thumbnail_${fileName.replace(".mp4",".jpg")}",
                                   description = description
                               )
                           )
                       }
                   }
               }
            }
        }
    }
}

//@Preview()
//@Composable
//fun PPreview() {
//    PublicationScreen(navController = rememberNavController(), uri = Uri.parse(""))
//}

@Composable
fun LoadingIndicator(isLoading: Boolean) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .background(Color.Yellow)
                .padding(horizontal = 24.dp, vertical = 10.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                )
        ) {
            Text(text = "Uploading ...")
        }
    } else {
        Box(
            modifier = Modifier
                .background(Color(0xff21ce99))
                .padding(horizontal = 24.dp, vertical = 10.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                )
        ) {
            Icon(Icons.Default.Done, contentDescription = "", tint = Color.White)
        }
    }
}