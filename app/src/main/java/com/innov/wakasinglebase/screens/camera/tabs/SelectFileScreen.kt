package com.innov.wakasinglebase.screens.camera.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.core.extension.LargeSpace
import com.innov.wakasinglebase.core.extension.MediumSpace
import com.innov.wakasinglebase.core.extension.SmallSpace
import com.innov.wakasinglebase.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectFileScreen(
    modifier: Modifier = Modifier,
    onSelectFileClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Galerie") })
        }, modifier = modifier
            .fillMaxSize()
            .background(White)
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Card(
                modifier = Modifier
                    //.padding(horizontal = 24.dp, vertical = 32.dp)
                    .fillMaxSize(),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp, horizontal = 24.dp)
                ) {
                    Text(
                        text = "Waka-Waka \nvideo",
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    MediumSpace()
                    Text(
                        text = "Cliquer ici pour selectionner votre contenu",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                    LargeSpace()
                    // Icon(, contentDescription = "")
                    Icon(
                        painter = painterResource(id = R.drawable.film_24),
                        contentDescription = "images" // decorative element
                    )
                    LargeSpace()
                    CustomButton(
                        buttonText = "Selectionner votre video",
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        onSelectFileClicked.invoke()
                    }
                    LargeSpace()
                    LargeSpace()
                    Image(
                        painter = painterResource(id = R.drawable.logo_tiktok_compose),
                        contentDescription = "images",
                        modifier=Modifier.size(56.dp)// decorative element
                    )
                    SmallSpace()
                    Text(text = "(Nous supportons une video de MAXIMUM 3 min.)", textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSelectFileScreen() {
    SelectFileScreen(
        onSelectFileClicked = {}
    )
}