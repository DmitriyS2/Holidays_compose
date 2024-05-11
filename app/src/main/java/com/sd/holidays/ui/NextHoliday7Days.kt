package com.sd.holidays.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sd.holidays.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NextHoliday7Days(vm: MainViewModel = viewModel(), navController: NavController) {
    val nextHoliday by vm.dataModelNextHoliday.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        //     verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(text = "Ближайшие праздники")
            },

            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("drawer")
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "backFromNextHoliday7Days"
                    )
                }
            }
        )
        when {
            nextHoliday?.loading == true -> {
                Log.d("MyLog", "nextHol loading")
            }

            nextHoliday?.error == true -> {
                Log.d("MyLog", "nextHol error")
            }

            //   infoCountry != null -> {
            else -> {
                Log.d("MyLog", "nextHol else")
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn() {
                    itemsIndexed(nextHoliday?.listDataHoliday ?: emptyList()) { _, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Страна: ${item.countryCode}", fontSize = 18.sp
                            )
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