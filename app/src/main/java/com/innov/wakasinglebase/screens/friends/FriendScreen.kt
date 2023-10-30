package com.innov.wakasinglebase.screens.friends

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@Composable
fun FriendScreen(
    navController: NavController,
    viewModel: FriendViewModel= hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    Scaffold {
        LazyColumn(modifier = Modifier.padding(it)){
            item {
                if(viewState?.isLoading==true){
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color= PrimaryColor
                    )
                }
                if (viewState?.error!=null){
                    Text(text = "${viewState?.error}")
                }
            }

            if (viewState?.friends!=null){
                if(viewState?.friends!!.isEmpty()){
                    item {
                        
                    }
                }else{
                    items(viewState?.friends!!){}
                }
            }
        }
    }
}