package com.innov.wakasinglebase.screens.camera.publication

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun PublicationScreen(
    navController: NavController,
    uri: Uri?,
    viewModel: PublicationViewModel= hiltViewModel()
) {
   Scaffold(
       topBar = {
           TopAppBar (
               title = {Text("Publication")},
               actions = {
                   IconButton(onClick = { /*TODO*/ }) {
                       Icon(Icons.Default.Done,"done")
                   }
               }
           )
       }
   ) {paddingValues ->
       Box(modifier = Modifier
           .padding(paddingValues)
           .fillMaxSize()){
           Column {
               //TODO adding design here
               Text("Dernier detail avant la publication final ")
           }
       }
   }
}