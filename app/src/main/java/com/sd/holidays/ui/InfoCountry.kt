package com.sd.holidays.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun InfoCountry(vm: MainViewModel = viewModel(), navController: NavController) {
    val infoCountry by vm.infoCountry.observeAsState()

    when {
        infoCountry?.loading == true -> {

        }

        infoCountry?.error == true -> {

        }

     //   infoCountry != null -> {
        else -> {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
           //     verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopAppBar(
                    title = {
                        Text(text = vm.selectedCountry)
                    },

                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate("drawer")
                        }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "backFromInfoAboutCountry")
                        }
                    }
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp, start = 5.dp, end = 5.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 5.dp, start = 5.dp, end = 5.dp),
                        text = "Официальное название", fontSize = 14.sp
                    )
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = infoCountry!!.info.officialName, fontSize = 24.sp
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, start = 5.dp, end = 5.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 5.dp, start = 5.dp, end = 5.dp),
                        text = "Код страны", fontSize = 14.sp
                    )
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = infoCountry!!.info.countryCode, fontSize = 24.sp
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, start = 5.dp, end = 5.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 5.dp, start = 5.dp, end = 5.dp),
                        text = "Регион", fontSize = 14.sp
                    )
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = infoCountry!!.info.region, fontSize = 24.sp
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
                    text = "Соседние страны", fontSize = 18.sp
                )
                LazyColumn() {
                    itemsIndexed(infoCountry?.info?.borders ?: emptyList()) { _, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clickable{
                                    vm.changeSelectedCountry(item.commonName)
                                    vm.getInfoAboutCountry(item.countryCode)
                                }
                        ) {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Страна: ${item.commonName}"
                            )
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Код страны: ${item.countryCode}"
                            )
                            Text(modifier = Modifier.padding(5.dp), text = "Регион: ${item.region}")
                        }
                    }
                }
            }
        }
    }


}