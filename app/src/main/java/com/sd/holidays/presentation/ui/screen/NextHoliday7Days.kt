package com.sd.holidays.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sd.holidays.presentation.ui.theme.Blue
import com.sd.holidays.presentation.viewmodel.MainViewModel
import com.sd.holidays.util.extensions.getDay
import com.sd.holidays.util.sealed.Routes
import com.sd.holidays.util.toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NextHoliday7Days(
    vm: MainViewModel = viewModel(),
    navController: NavController,
) {

    val context = LocalContext.current

    val nextHoliday by vm.dataModelNextHoliday.observeAsState()

    val checkedState by vm.stateNotification.observeAsState()
    val textColor = remember { mutableStateOf(Color.Unspecified) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(text = "Ближайшие праздники") },
            colors = TopAppBarColors(
                containerColor = Blue,
                scrolledContainerColor = Blue,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
            ),
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate(Routes.Drawer.route)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "backFromNextHoliday7Days"
                    )
                }
            }
        )
        when {
            nextHoliday?.error == true -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Blue),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "Что-то пошло не так...Попробуйте позже")
                    Button(onClick = {
                        vm.loadDataCountry()
                    }) {
                        Text(text = "Retry")
                    }
                }
            }

            else -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Уведомления о праздниках",
                        fontSize = 16.sp,
                        color = textColor.value,
                        modifier = Modifier.padding(8.dp, 0.dp)
                    )
                    Switch(
                        checked = checkedState ?: false,
                        onCheckedChange = {
                            vm.setStateNotification(it) //checkedState = it
                            if (checkedState == true) {
                                textColor.value = Color(0xFF6650a4)
                                vm.setAlarm()
                                toast(context, "Уведомления включены")
                            } else {
                                textColor.value = Color.Unspecified
                                vm.cancelAlarm()
                                toast(context, "Уведомления выключены")
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn {
                    itemsIndexed(nextHoliday?.listDataHoliday ?: emptyList()) { _, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                        ) {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Страна: ${item.countryCode}",
                                fontSize = 18.sp,
                            )
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Дата: ${item.date.getDay()}",
                            )
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Местное название: ${item.localName}",
                            )
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Название: ${item.name}",
                            )
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Тип: ${item.types.joinToString(", ")}",
                            )
                        }
                    }
                }
                if (nextHoliday?.loading == true) {
                    Box {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}