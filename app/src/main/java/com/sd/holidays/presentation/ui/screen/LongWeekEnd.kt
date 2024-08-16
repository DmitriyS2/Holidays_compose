package com.sd.holidays.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sd.holidays.presentation.ui.theme.Blue
import com.sd.holidays.util.checkInputText
import com.sd.holidays.presentation.viewmodel.MainViewModel
import com.sd.holidays.util.sealed.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LongWeekEnd(
    vm: MainViewModel = viewModel(),
    navController: NavController,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {

    val longWeekEnd by vm.dataModelLongWeekEnd.observeAsState()

    val longWeekEndYear by vm.longWeekEndYear.observeAsState()

    val text = remember {
        mutableStateOf("")
    }
    val isError = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue),
  //          .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(text = vm.selectedCountry)
            },
            colors = TopAppBarColors(
                containerColor = Blue,
                scrolledContainerColor = Blue,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
            ),
            navigationIcon = {
                IconButton(onClick = {
                    vm.getListLongWeekEnd("")
                    navController.navigate(Routes.Drawer.route)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "backFromLongWeekEnd"
                    )
                }
            }
        )
        OutlinedTextField(
            value = text.value,
            onValueChange = { newText ->
                text.value = newText
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = {
                if (checkInputText(text.value)) {
                    if (text.value!=vm.longWeekEndYear.value) {
                        vm.setLongWeekEndYear(text.value)
                        vm.getListLongWeekEnd(text.value)
                    }

                    isError.value = false
                    keyboardController?.hide()
                    focusManager.clearFocus()
                } else {
                    isError.value = true
                }
            }),
            placeholder = {
                Text(text = "Введите год")
            },
            isError = isError.value,
            supportingText = {
                if (isError.value) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Укажите год в интервале 1974-2074",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            singleLine = true,
            leadingIcon = {
                if (isError.value)
                    Icon(
                        Icons.Default.Warning,
                        "error",
                        tint = MaterialTheme.colorScheme.error
                    )
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (checkInputText(text.value)) {
                        if (text.value!=vm.longWeekEndYear.value) {
                            vm.setLongWeekEndYear(text.value)
                            vm.getListLongWeekEnd(text.value)
                        }
             //           vm.getListLongWeekEnd(text.value)
                        isError.value = false
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    } else {
                        isError.value = true
                    }
                }) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "ok")
                }
            },
        )

        Text(text = "Длинные выходные в $longWeekEndYear", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(15.dp))

        when {
            longWeekEnd?.loading == true -> {

            }

            longWeekEnd?.error == true -> {

            }

            else -> {
                LazyColumn() {
                    itemsIndexed(longWeekEnd?.listDataLongWeekEnd ?: emptyList()) { _, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Начало: ${item.startDate}"
                            )
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Завершение: ${item.endDate}"
                            )
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Длительность: ${item.dayCount} дн"
                            )
                        }
                    }
                }
            }
        }
    }
}



