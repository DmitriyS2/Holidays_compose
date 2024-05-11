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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sd.holidays.R
import com.sd.holidays.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(
    vm: MainViewModel = viewModel(),
    navController: NavController,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

//    val keyboardController = LocalSoftwareKeyboardController.current
//    val focusManager = LocalFocusManager.current

    //  val listCountries by vm.dataCountry.observeAsState()
    val modelListCountries by vm.dataModelCountry.observeAsState()
 //   var selectedCountry = ""

    val searchText = remember {
        mutableStateOf("")
    }

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
                        Text(text = "О стране", fontSize = 22.sp)
                    },
                    selected = false,
                    onClick = {
                        vm.getInfoAboutCountry(vm.mapCountry[vm.selectedCountry])
                        scope.launch {
                            drawerState.close()
                            navController.navigate("info")
                        }
                    })
                NavigationDrawerItem(
                    label = {
                        Text(text = "Длинные выходные", fontSize = 22.sp)
                    },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("longWeekEnd")
                        }
                    })
                NavigationDrawerItem(
                    label = {
                        Text(text = "Праздники в стране", fontSize = 22.sp)
                    },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("publicHoliday")
                        }
                    })
                NavigationDrawerItem(
                    label = {
                        Text(text = "Назад", fontSize = 22.sp)
                    },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    })
            }
        },
        content = {
            when {
                modelListCountries?.loading == true -> {
                    Log.d("MyLog", "loading drawer")
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box() {
                            CircularProgressIndicator()
                        }
                    }
                }

                modelListCountries?.error == true -> {
                    Log.d("MyLog", "error drawer")
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
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
                    Log.d("MyLog", "else drawer")

                    Column(Modifier.fillMaxSize()) {
                        TopAppBar(
                            title = {
                                Text(text = "Все страны")
                            },
                            actions = {
                                IconButton(onClick = {
                                    vm.getListNextHoliday()
                                    navController.navigate("nextHoliday7Days")
                                }) {
                                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "toHolidayToday")
                                }
                            }
                        )
                        SearchBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            query = searchText.value,
                            onQueryChange = { text ->
                                searchText.value = text
                                vm.searchCountry(text)
                            },
                            placeholder = {
                                Text(text = "Введите название страны")
                            },
                            onSearch = {
                                searchText.value = ""
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                       },
                            active = false,
                            onActiveChange = {
                            vm.searchCountry("")
                            },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                            }
                        ) {

                        }
                        Spacer(modifier = Modifier.height(25.dp))
                        LazyColumn() {
                            //     itemsIndexed(listCountries ?: emptyList()) { _, item ->
                            itemsIndexed(
                                modelListCountries?.listCountry ?: emptyList()
                            ) { _, item ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp)
                                        .clickable {
                                            vm.searchCountry("")
                                            searchText.value = ""
                                            vm.changeSelectedCountry(item)
                                            scope.launch {
                                                keyboardController?.hide()
                                                delay(400)
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
        }
    )
}