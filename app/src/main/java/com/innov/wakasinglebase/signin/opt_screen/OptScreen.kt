package com.innov.wakasinglebase.signin.opt_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.common.OtpTextField
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@Composable
fun OptScreen(
    navController: NavController,
    optViewModel: OptViewModel = hiltViewModel(),
    phone: String?
) {

    var optState by optViewModel.uiState
    LaunchedEffect(key1 = optState.success){
        if (optState.success){
            navController.navigate(DestinationRoute.MAIN_NAV_ROUTE)
        }
    }
    Scaffold {
        var code by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            100.dp.Space()
            Text(text = "Waka-Waka", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            32.dp.Space()
            Text(
                text = "Vous allez recevoir un SMS code au $phone, ce pour verifier que c'est un humain!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
            )
            32.dp.Space()
            OtpTextField(
                otpText = code,
                otpCount = 6,
                onOtpTextChange = { value, isDone ->
                    code = value
                    optViewModel.onEvent(CodeEvent.OnCodeEntered(value))
                }
            )

            120.dp.Space()
            if (optState.isLoading){
                CircularProgressIndicator(color = PrimaryColor)
            }else{
                CustomButton(buttonText = "Continuer",
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 18.dp),
                    shape = RoundedCornerShape(20),
                    isEnabled = optViewModel.isValid(),
                ) {
                    optViewModel.onEvent(
                        CodeEvent.OnSubmit("$phone")
                    )
                }
            }

            if (optState.error!=null){
                Text(text = "${optState.error} !!!", color = Color.Red)
            }
        }
    }
}