package com.sd.holidays.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import com.sd.holidays.AlarmReceiver
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
                val vm: MainViewModel = viewModel()
                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current

                var alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager
//                var alarmIntent:PendingIntent = Intent(this, AlarmReceiver::class.java).let { intent->
//               //     intent.putExtra("key", "hello")
//                    PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//                }


                Box(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = "drawer"
                    ) {
                        composable("drawer",
//                            enterTransition = { slideInHorizontally() },
//                            exitTransition = { slideOutHorizontally() },
//                            popEnterTransition = { slideInHorizontally() },
//                            popExitTransition = { slideOutHorizontally() }
                        ) {
                            Drawer(
                                vm,
                                navController,
                                keyboardController,
                                focusManager
                            )
                        }
                        composable("info",
//                            enterTransition = { slideInHorizontally(animationSpec =  tween(durationMillis = 100))},
//                            exitTransition = { slideOutHorizontally (animationSpec = tween(durationMillis = 100))},
//                            enterTransition = { slideInHorizontally() },
//                            exitTransition = { slideOutHorizontally() },
//                            popEnterTransition = { slideInHorizontally() },
//                            popExitTransition = { slideOutHorizontally() }
                        ) {

                            InfoCountry(vm, navController)
                        }
                        composable("longWeekEnd") {
                            LongWeekEnd(vm, navController, keyboardController, focusManager)
                        }
                        composable("publicHoliday") {
                            PublicHoliday(vm, navController, keyboardController, focusManager)
                        }
                        composable("nextHoliday7Days") {
                            NextHoliday7Days(vm, navController, this@MainActivity)
                        }
                    }
                }
            }
        }
    }

}

fun hello() {

  //  Log.d("MyLog", "from hello=${viewModel.dataModelHoliday.value?.listDataHoliday}")

    // viewModel.getListNextHoliday()
}
