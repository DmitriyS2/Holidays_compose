package com.sd.holidays.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sd.holidays.util.checkInputText
import com.sd.holidays.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicHoliday(
    vm: MainViewModel,
    navController: NavHostController,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {

    val publicHoliday by vm.dataModelHoliday.observeAsState()

    val text = remember {
        mutableStateOf("")
    }
    val isError = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(text = vm.selectedCountry)
            },
            navigationIcon = {
                IconButton(onClick = {
                    vm.getListHoliday("")
                    navController.navigate("drawer")
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "backFromPublicHoliday"
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
                    vm.getListHoliday(text.value)
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
                        vm.getListHoliday(text.value)
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
        Text(text = "Праздники", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(15.dp))

        when {
            publicHoliday?.loading == true -> {

            }

            publicHoliday?.error == true -> {

            }

            else -> {
                LazyColumn() {
                    itemsIndexed(publicHoliday?.listDataHoliday ?: emptyList()) { _, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Дата: ${item.date}"
                            )
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Местное название: ${item.localName}"
                            )
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Название: ${item.name}"
                            )
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Тип: ${item.types.joinToString(", ")}"
                            )
                        }
                    }
                }
            }
        }
    }
}
