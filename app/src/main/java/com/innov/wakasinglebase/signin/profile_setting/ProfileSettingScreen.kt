package com.innov.wakasinglebase.signin.profile_setting

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.common.AvatarProfile
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.core.utils.FileUtils
import com.innov.wakasinglebase.ui.theme.PrimaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingScreen(
    navController: NavController,
    viewModel: ProfileSettingViewModel= hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val updateUserState by viewModel.updateUserState.collectAsState()
    val state by viewModel.state.collectAsState()
    val uploadImageState by viewModel.uploadState.collectAsState()

    val context = LocalContext.current


    val image= rememberSaveable {
        mutableStateOf("")
    }
    val fileLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                if (
                    uri != null
                ) {
                    image.value=uri.toString()
                    val t = FileUtils.getFileNameFromUri(context, uri)

                    if (t != null) {
                        viewModel.onTriggerEvent(ProfileSettingEvent.OnUploadImageOns3(uri, t))
                        // val avatar="https://d2y4y6koqmb0v7.cloudfront.net/$t"
                        viewModel.onTriggerEvent(ProfileSettingEvent.OnAvatarEntered(t))
                    }
                }

            })

    LaunchedEffect(key1 = viewState?.userModel) {
        if (viewState?.userModel != null) {
            if (viewState?.userModel?.profilePic!=null){
                val avatar=viewState?.userModel?.profilePic?.split(".net/")
                viewModel.onTriggerEvent(ProfileSettingEvent.OnAvatarEntered("${avatar?.get(1)}"))
            }
            if (viewState?.userModel?.name?.isNotEmpty()==true){
                viewModel.onTriggerEvent(ProfileSettingEvent.OnNameEntered(state.name?:""))
            }

        }
    }

    LaunchedEffect(key1 = updateUserState.success) {
        if (updateUserState.success) {
            navController.navigate(DestinationRoute.MAIN_NAV_ROUTE)
        }
    }





    if (viewState?.userModel != null) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(title = {
                    Text(text = "Profile")
                })
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                72.dp.Space()
                val url=if(image.value=="")  viewState?.userModel?.profilePic else image.value
                AvatarProfile(image =url , onClick = {
                    fileLauncher.launch("image/*")
                }, state = uploadImageState,size = 80.dp)

                72.dp.Space()

                // Text(text  = "${firstProfileViewModel.state?.value.name} ")
                Column (horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    ){
                    Text(text = "Name", color= Color.Gray, fontSize = 12.sp)
                    TextField(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        label = {
                            Text(text = "Your name")
                        },
                        value = "${viewModel.name} ", onValueChange = {
                            viewModel.name = it.trim()
                            viewModel.onTriggerEvent(ProfileSettingEvent.OnNameEntered(it.trim()))
                        })
                }
                16.dp.Space()


                88.dp.Space()
                if (updateUserState.isLoading) {
                    CircularProgressIndicator(color = PrimaryColor)
                } else {
                    CustomButton(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        shape = RoundedCornerShape(16),
                        buttonText = "continue"
                    ) {
                        viewModel.onTriggerEvent(ProfileSettingEvent.OnSubmit)
                    }
                }

                if (updateUserState.error != null) {
                    24.dp.Space()
                    Text(text = "${updateUserState.error} ", color = Color.Red, fontSize = 12.sp)
                }

            }
        }
    } else if (viewState?.isLoading==true) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = PrimaryColor)
        }
    } else {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Unable to load user, please reload user")
            40.dp.Space()
            CustomButton(buttonText = "Reload") {
                viewModel.onTriggerEvent(ProfileSettingEvent.ReloadUser)
            }
        }
    }
}