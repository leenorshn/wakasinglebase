package com.innov.wakasinglebase.signin.name_and_avatar

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.innov.wakasinglebase.common.Avatar
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.common.CustomTextField
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.core.utils.FileUtils.getFileNameFromUri
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FirstProfileScreen(
    navController: NavController,
    firstProfileViewModel: FirstProfileViewModel= hiltViewModel()
) {
    
    val state by firstProfileViewModel.state.collectAsState()
    val updateUserState by firstProfileViewModel.updateUserState.collectAsState()
    
    var name by remember {
        mutableStateOf(TextFieldValue())
    }

    val context = LocalContext.current
    val multiplePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    )
    var fileName by remember { mutableStateOf("") }
    var uriG by remember { mutableStateOf<Uri?>(null) }
    val fileLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(),
            onResult = {uri->

                var t= getFileNameFromUri(context,uri!!)

                fileName=t ?:""
                uriG=uri



                firstProfileViewModel.onEvent(FirstProfileEvent.OnAvatarChanged(uri,fileName))


            })

    LaunchedEffect(key1 = uriG){
        if (uriG!=null){
            firstProfileViewModel.onEvent(FirstProfileEvent.OnImageUpload)
        }
    }

    LaunchedEffect(key1 = updateUserState.success, ){
        if(updateUserState.success){
            navController.navigate("main")
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(title = { 
                Text(text = "Profile")
            })
        }
    ){
        Column(modifier = Modifier
            .padding(it)
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,) {
            72.dp.Space()
            Avatar(image =state.userModel?.profilePic?: "https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/default-profile-picture-grey-male-icon.png", onClick ={
                fileLauncher.launch("image/*")
            }, size = 80.dp )
            56.dp.Space()
            CustomTextField(label = "Votre nom", value =name , onChange = {
                name=it
                firstProfileViewModel.onEvent(FirstProfileEvent.OnNameEntered(name.text))
            })
            88.dp.Space()
            if (updateUserState.isLoading){
                CircularProgressIndicator(color= PrimaryColor)
            }else{
                CustomButton(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    shape = RoundedCornerShape(16),
                    buttonText = "finaliser") {
                    firstProfileViewModel.onEvent(FirstProfileEvent.OnSubmit)
                }
            }

            if(updateUserState.error!=null){
                24.dp.Space()
                Text(text = "${updateUserState.error} ",color= Color.Red, fontSize = 12.sp)
            }

        }
    }
}