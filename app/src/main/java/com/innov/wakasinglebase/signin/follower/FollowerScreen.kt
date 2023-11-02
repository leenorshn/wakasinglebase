package com.innov.wakasinglebase.signin.follower

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@Composable
fun FollowItem(user: UserModel,onFollowingUser: (id: String)->Unit) {
    ListItem(headlineContent = {
        (if (user.name=="")  {"${user.phone}"} else {user?.name})?.let { Text(text = it) }
    },
        leadingContent = {
            if(user.profilePic!=null){
                AsyncImage(model = user.profilePic, contentDescription = "")
            }else{
                AsyncImage(model = user.profilePic, contentDescription = "")
            }

        },
        trailingContent = {
            CustomButton(buttonText = "Follow" ) {
                onFollowingUser.invoke(user.uid!!)
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowerScreen(
    navController: NavController,
    viewModel: FollowerViewModel= hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "")},
                actions = {
                TextButton(
                    enabled = viewModel.isValidFollowerList(),
                    onClick = {
                    navController.navigate(DestinationRoute.MAIN_NAV_ROUTE)
                }) {
                    Text("Continue")
                }
            })
        }
    ){
        LazyColumn(modifier = Modifier.padding(it)){
            item { 
                Text(text = "Connaissez-vous ces personnes")
            }
            if (viewState?.isLoading==true){
                item {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color= PrimaryColor
                    )
                }
            }

            if (viewState?.listUser!=null){
                items(viewState?.listUser!!){
                    FollowItem(user=it, onFollowingUser = {})
                }
            }
        }
    }
}