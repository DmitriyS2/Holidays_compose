package com.sd.holidays.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sd.holidays.viewmodel.MainViewModel

@Composable
fun InfoCountry(vm:MainViewModel= viewModel(), navController: NavController) {
    val infoCountry by vm.infoCountry.observeAsState()

    when {
        infoCountry?.loading==true -> {

        }
        infoCountry?.error==true -> {

        }
        infoCountry != null -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center, text = infoCountry!!.info.commonName, )
            }
        }
    }


}