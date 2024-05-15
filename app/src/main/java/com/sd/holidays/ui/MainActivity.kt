package com.sd.holidays.ui

import android.app.AlarmManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sd.holidays.ui.screen.Drawer
import com.sd.holidays.ui.screen.InfoCountry
import com.sd.holidays.ui.screen.LongWeekEnd
import com.sd.holidays.ui.screen.NextHoliday7Days
import com.sd.holidays.ui.screen.PublicHoliday
import com.sd.holidays.ui.theme.HolidaysTheme
import com.sd.holidays.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HolidaysTheme {

                val navController = rememberNavController()
                val vm: MainViewModel = viewModel()
                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current

                var alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager

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
                        composable("info") {
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

