package com.innov.wakasinglebase.signin.phoneScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.ui.theme.PrimaryColor
import com.simon.xmaterialccp.component.MaterialCountryCodePicker
import com.simon.xmaterialccp.data.ccpDefaultColors
import com.simon.xmaterialccp.data.utils.checkPhoneNumber
import com.simon.xmaterialccp.data.utils.getDefaultLangCode
import com.simon.xmaterialccp.data.utils.getDefaultPhoneCode
import com.simon.xmaterialccp.data.utils.getLibCountries


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneScreen(
    navController: NavController,
    phoneViewModel: PhoneViewModel = hiltViewModel()
) {

    val uiState by phoneViewModel.uiState
    val verificationState by phoneViewModel.verificationState.collectAsState()
    val context = LocalContext.current
    var phoneCode by remember { mutableStateOf(getDefaultPhoneCode(context)) }
    val phoneNumber = rememberSaveable { mutableStateOf("") }
    var defaultLang by rememberSaveable { mutableStateOf(getDefaultLangCode(context)) }
    var isValidPhone by remember { mutableStateOf(true) }




    LaunchedEffect(key1 = verificationState.success ){
        if (verificationState.success) {
            navController.navigate(
                DestinationRoute.OPT_SCREEN_ROUTE.replace(
                    "{phone}",
                    "$phoneCode${phoneNumber.value}"
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
            64.dp.Space()
            Text(text = "Waka-Waka", fontWeight = FontWeight.Medium, fontSize = 32.sp)
            Image(painter = painterResource(id = R.drawable.logo_tiktok_compose), contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(56.dp))
            12.dp.Space()
            Text(text = "Phone verification", fontSize = 24.sp)
            56.dp.Space()



            MaterialCountryCodePicker(
                pickedCountry = {
                    phoneCode = it.countryPhoneCode
                    defaultLang = it.countryCode
                },
                defaultCountry = getLibCountries().single { it.countryCode == defaultLang },
                error = !isValidPhone,
                text = phoneNumber.value,
                onValueChange = {
                    phoneNumber.value = it
                    phoneViewModel.onEvent(PhoneEvent.OnPhoneEntered("$phoneCode${phoneNumber.value}"))
                },
                searchFieldPlaceHolderTextStyle = MaterialTheme.typography.bodyMedium,
                searchFieldTextStyle = MaterialTheme.typography.bodyMedium,
                phonenumbertextstyle = MaterialTheme.typography.bodyMedium,
                countrytextstyle = MaterialTheme.typography.bodyMedium,
                countrycodetextstyle = MaterialTheme.typography.bodyMedium,
                showErrorText = true,
                showCountryCodeInDIalog = true,
                showDropDownAfterFlag = true,
                textFieldShapeCornerRadiusInPercentage = 16,
                searchFieldShapeCornerRadiusInPercentage = 16,
                appbartitleStyle = MaterialTheme.typography.titleLarge,
                countryItemBgShape = RoundedCornerShape(5.dp),
                showCountryFlag = true,
                showCountryCode = true,
                isEnabled = true,
                colors = ccpDefaultColors(
                    primaryColor = MaterialTheme.colorScheme.primary,
                    errorColor = MaterialTheme.colorScheme.error,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    surfaceColor = MaterialTheme.colorScheme.surface,
                    outlineColor = MaterialTheme.colorScheme.outline,
                    disabledOutlineColor = MaterialTheme.colorScheme.outline.copy(0.1f),
                    unfocusedOutlineColor = MaterialTheme.colorScheme.onBackground.copy(0.3f),
                    textColor = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                    cursorColor = MaterialTheme.colorScheme.primary,
                    topAppBarColor = MaterialTheme.colorScheme.surface,
                    countryItemBgColor = MaterialTheme.colorScheme.surface,
                    searchFieldBgColor = MaterialTheme.colorScheme.surface,
                    dialogNavIconColor = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                    dropDownIconTint = MaterialTheme.colorScheme.onBackground.copy(0.7f)

                )
            )


            val checkPhoneNumber = checkPhoneNumber(
                phone = phoneNumber.value,
                fullPhoneNumber = "$phoneCode${phoneNumber.value}",
                countryCode = defaultLang
            )




            12.dp.Space()
            Text(
                text = "You'll receiver un SMS code on the number you'll Enter.",
                color = Color.Gray
            )
            110.dp.Space()

            CustomButton(
                buttonText = "Continue",
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
                        Text(text = stringResource(R.string.do_you_want_to_use_this_phone_number))
                        Text(text = "${"$phoneCode${phoneNumber.value}"} ?")
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
                                buttonText = "Continue",
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


    }}



