package com.innov.wakasinglebase.screens.myprofil.recharge

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.core.extension.Space


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RechargeScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "")
            })
        }

    )
    {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val annotatedString = buildAnnotatedString {
                withStyle(style = TextStyle(
                    lineHeight = 1.2.em,
                    fontSize = 28.sp,  fontWeight = FontWeight.Normal,
                ).toSpanStyle()) {
                    append("To recharge your \n")
                }
                withStyle(style = TextStyle(
                    lineHeight = 1.5.em,
                    fontSize = 28.sp,  fontWeight = FontWeight.Bold,
                ).toSpanStyle()) {
                    append("\n" +
                            "Waka-Waka \n\n")
                }
                withStyle(style = TextStyle(
                    fontSize = 28.sp,  fontWeight = FontWeight.Normal,
                    lineHeight = 1.3.em,
                ).toSpanStyle()) {
                    append("account, you must send money\n on this number: ")
                }
            }
            Text(
                text = annotatedString,
                lineHeight = 1.5.em,
                textAlign = TextAlign.Center,
            )
            24.dp.Space()
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "09 78 15 43 29", fontSize =24.sp )
                    Text(text = "AirtelMoney", modifier = Modifier.align(alignment = Alignment.BottomEnd))
                }
            }
            24.dp.Space()
            Text(
                text = "Your account will be directly recharge.",
                lineHeight = 1.2.em,
                fontSize = 28.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Normal,
            )
            56.dp.Space()
            CustomButton(
                containerColor=Color.Black,
                shape = RoundedCornerShape(16),
                modifier = Modifier
                    .width(180.dp),
                    //.background(color = Color.Black, ),
                buttonText = "Back"
            ) {
                navController.navigateUp()
            }
        }
    }
}