package com.sd.holidays.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current
                Box(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = "drawer"
                    ) {
                        composable("drawer",
                            enterTransition = { slideInHorizontally() },
                            exitTransition = { slideOutHorizontally ()},
                            popEnterTransition = { slideInHorizontally()},
                            popExitTransition = { slideOutHorizontally()}
                        ){
                            Drawer(context = this@MainActivity, vm, navController, keyboardController, focusManager)
                        }
                        composable("info",
//                            enterTransition = { slideInHorizontally(animationSpec =  tween(durationMillis = 100))},
//                            exitTransition = { slideOutHorizontally (animationSpec = tween(durationMillis = 100))},
                            enterTransition = { slideInHorizontally()},
                            exitTransition = { slideOutHorizontally ()},
                            popEnterTransition = { slideInHorizontally()},
                            popExitTransition = { slideOutHorizontally()}
                        ) {

                            InfoCountry(vm, navController)
                        }
                        composable("longWeekEnd") {
                            LongWeekEnd(vm, navController, keyboardController, focusManager)
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
