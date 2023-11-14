package com.innov.wakasinglebase.screens.myprofil.edit_profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.innov.wakasinglebase.common.Avatar
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.core.utils.FileUtils
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstProfileScreen(
    navController: NavController,
    viewModel: FirstProfileViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val updateUserState by viewModel.updateUserState.collectAsState()
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
                        viewModel.onEvent(EditProfileEvent.OnUploadImageOns3(uri, t))
                       // val avatar="https://d2y4y6koqmb0v7.cloudfront.net/$t"
                        viewModel.onEvent(EditProfileEvent.OnAvatarEntered(t))
                    }
                }

            })

    LaunchedEffect(key1 = state.userModel) {
        if (state.userModel != null) {
            if (state.userModel?.profilePic!=null){
                val avatar=state.userModel?.profilePic?.split(".net/")
                viewModel.onEvent(EditProfileEvent.OnAvatarEntered("${avatar?.get(1)}"))
            }
            if (state.userModel?.name?.isNotEmpty()==true){
                viewModel.onEvent(EditProfileEvent.OnNameEntered(state.name?:""))
            }

            if (state.userModel?.bio?.isNotEmpty()==true){
                viewModel.onEvent(EditProfileEvent.OnBioEntered(state.bio?:""))
            }
        }
    }

    LaunchedEffect(key1 = updateUserState.success) {
        if (updateUserState.success) {
            navController.navigateUp()
            viewModel.onEvent(EditProfileEvent.ReloadUser)
           // Toast.makeText(context, "User updated", Toast.LENGTH_LONG).show()
        }
    }

//    LaunchedEffect(key1 = uploadImageState.success, ){
//        if (uploadImageState.isLoading){
//            viewModel.onEvent(EditProfileEvent.ReloadUser)
//        }
//    }



    if (state.userModel != null) {
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
                    val url=if(image.value=="")  state.userModel?.profilePic else image.value
                    Avatar(image =url , onClick = {
                        fileLauncher.launch("image/*")
                    }, state = uploadImageState,size = 80.dp)

                72.dp.Space()

                // Text(text  = "${firstProfileViewModel.state?.value.name} ")
                Row (horizontalArrangement = Arrangement.Center){
                    Text(text = "Name", modifier = Modifier
                        .width(100.dp)
                        .padding(12.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        label = {
                            Text(text = "Your name")
                        },
                        value = "${viewModel.name} ", onValueChange = {
                            viewModel.name = it
                            viewModel.onEvent(EditProfileEvent.OnNameEntered(it.trim()))
                        })
                }
                16.dp.Space()
                Row (horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top){
                    Text(text = "Bio", modifier = Modifier
                        .width(100.dp)
                        .padding(12.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(100.dp),
                        label = {
                            Text(text = "Discribe your self")
                        },
                        value = "${viewModel.bio} ", onValueChange = {
                            viewModel.bio = it
                            viewModel.onEvent(EditProfileEvent.OnBioEntered(it.trim()))
                        })
                }

                88.dp.Space()
                if (updateUserState.isLoading) {
                    CircularProgressIndicator(color = PrimaryColor)
                } else {
                    CustomButton(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        shape = RoundedCornerShape(16),
                        isEnabled = viewModel.isValid(),
                        buttonText = "continue"
                    ) {
                        viewModel.onEvent(EditProfileEvent.OnSubmit)
                    }
                }

                if (updateUserState.error != null) {
                    24.dp.Space()
                    Text(text = "${updateUserState.error} ", color = Color.Red, fontSize = 12.sp)
                }

            }
        }
    } else if (state.userIsLoading) {
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

            }
        }
    }
}

    
