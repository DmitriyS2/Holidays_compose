package com.sd.holidays.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sd.holidays.ui.theme.HolidaysTheme
import com.sd.holidays.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

 //   private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HolidaysTheme {
                val selectedCountry = remember {
                    mutableStateOf("")
                }
//                val listCountries = remember {
//                    mutableStateOf(emptyList<String>())
//                }
//                viewModel.dataCountry.observe(this) {
//                    listCountries.value = it
//                }

                val navController = rememberNavController()
                val vm:MainViewModel = viewModel()
                Box(modifier = Modifier.fillMaxSize()) {


                    NavHost(
                        navController = navController,
                        startDestination = "drawer"
                    ) {
                        composable("drawer") {
                            Drawer(context = this@MainActivity, vm, navController)
                        }
                        composable("info") {
                            InfoCountry(vm, navController)
                        }
                    }
                }
//            Drawer(
//                context = this,
//          //      listCountries,
//              //  viewModel,
//               )
            }
        }
    }
}
