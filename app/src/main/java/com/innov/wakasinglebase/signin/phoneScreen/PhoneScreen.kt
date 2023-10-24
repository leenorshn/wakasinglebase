package com.innov.wakasinglebase.signin.phoneScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.common.PhoneTextField
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneScreen(
    navController: NavController,
    phoneViewModel: PhoneViewModel = hiltViewModel()
) {

    val uiState by phoneViewModel.uiState
    val verificationState by phoneViewModel.verificationState.collectAsState()

    var phone by remember {
        mutableStateOf(TextFieldValue())
    }
    val maxChar = 13

    LaunchedEffect(key1 = verificationState.success ){
        if (verificationState.success) {
            navController.navigate(
                DestinationRoute.OPT_SCREEN_ROUTE.replace(
                    "{phone}",
                    phone.text
                )
            )
        }
    }
    Scaffold(

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            72.dp.Space()
            Text(text = "Waka-Waka", fontWeight = FontWeight.Medium, fontSize = 32.sp)
            12.dp.Space()
            Text(text = "Phone verification", fontSize = 24.sp)
            72.dp.Space()





            PhoneTextField(label = "Telephone", phone = phone, onValueChange = { value ->
                if (phone.text.length < maxChar) {
                    phone = value
                    phoneViewModel.onEvent(PhoneEvent.OnPhoneEntered(value.text))
                }
            })


            12.dp.Space()
            Text(
                text = "You'll receiver un SMS code on the number you'll Enter.",
                color = Color.Gray
            )
            148.dp.Space()

            CustomButton(
                buttonText = "Continuer",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 18.dp),
                shape = RoundedCornerShape(20),
                isEnabled = phoneViewModel.isValid(),
            ) {
                phoneViewModel.onEvent(
                    PhoneEvent.OnSubmit
                )
            }
            if (verificationState.error != null) {
                32.dp.Space()
                Text(text = "${verificationState.error}", color = Color.Red, fontSize = 12.sp)
            }
        }
        if (uiState.showDialog) {
            AlertDialog(
                onDismissRequest = { phoneViewModel.onEvent(PhoneEvent.HideDialog) },
            ) {

                Card() {
                    Column(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Voulez-vous utiliser ce numero")
                        Text(text = "${phone.text} ?")
                        32.dp.Space()
                        if (verificationState.isLoading) {
                            CircularProgressIndicator(color = PrimaryColor)
                            16.dp.Space()
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            TextButton(
                                onClick = { phoneViewModel.onEvent(PhoneEvent.HideDialog) },
                                modifier = Modifier.padding(8.dp),
                            ) {
                                Text("Dismiss")
                            }

                            CustomButton(
                                buttonText = "Continuer",
                                modifier = Modifier
                                    .padding(vertical = 18.dp),
                                shape = RoundedCornerShape(20),
                                isEnabled = !verificationState.isLoading
                            ) {
                                phoneViewModel.onEvent(PhoneEvent.OnSubmitVerification)


                            }
                        }

                    }
                }
            }


        }
    }

}

