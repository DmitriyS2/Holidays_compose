package com.sd.holidays.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sd.holidays.R
import com.sd.holidays.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(
    context: Context,
//    listCountries: MutableState<List<String>>,
    //   viewModel: MainViewModel,
    vm: MainViewModel = viewModel(),
    navController: NavController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val listCountries by vm.dataCountry.observeAsState()
    val modelListCountries by vm.dataModelCountry.observeAsState()
    var selectedCountry = ""

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.holidays),
                    contentDescription = "header",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(45.dp))
                NavigationDrawerItem(
                    label = {
                        Text(text = "Информация о стране", fontSize = 22.sp)
                    },
                    selected = false,
                    onClick = {
                        Toast.makeText(
                            context,
                            "Информация ${vm.mapCountry[selectedCountry]}",
                            Toast.LENGTH_SHORT
                        ).show()
                        scope.launch {
                            drawerState.close()
                        }
                        vm.getInfoAboutCountry(vm.mapCountry[selectedCountry])
                        navController.navigate("info")
                    })
                NavigationDrawerItem(
                    label = {
                        Text(text = "Длинные выходные", fontSize = 22.sp)
                    },
                    selected = false,
                    onClick = {
                        Toast.makeText(context, "Выходные", Toast.LENGTH_SHORT).show()
                        scope.launch {
                            drawerState.close()
                        }
                    })
                NavigationDrawerItem(
                    label = {
                        Text(text = "Праздники в году", fontSize = 22.sp)
                    },
                    selected = false,
                    onClick = {
                        Toast.makeText(context, "Праздники", Toast.LENGTH_SHORT).show()
                        scope.launch {
                            drawerState.close()
                        }
                    })
                NavigationDrawerItem(
                    label = {
                        Text(text = "Назад", fontSize = 22.sp)
                    },
                    selected = false,
                    onClick = {
                        //    Toast.makeText(context, "Назад", Toast.LENGTH_SHORT).show()
                        scope.launch {
                            drawerState.close()
                        }
                    })
            }
        },
        content = {
            when {
                modelListCountries?.loading == true -> {
                    Log.d("MyLog", "loading")
                    Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Box() {CircularProgressIndicator()}
                    }
                }

                modelListCountries?.error == true -> {
                    Log.d("MyLog", "error")
                    Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(text = "Что-то пошло не так...Попробуйте позже")
                        Button(onClick = {
                            vm.loadDataCountry()
                        }) {
                            Text(text = "Retry")
                        }
                    }
                }

                else -> {
                    Log.d("MyLog", "else")
                    LazyColumn() {
                        itemsIndexed(listCountries ?: emptyList()) { _, item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                                    .clickable {
                                        selectedCountry = item
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }
                            ) {
                                Text(
                                    text = item,
                                    fontSize = 26.sp,
                                    modifier = Modifier
                                        .padding(vertical = 15.dp)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}