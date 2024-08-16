package com.sd.holidays.presentation.ui

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
import com.sd.holidays.presentation.ui.screen.Drawer
import com.sd.holidays.presentation.ui.screen.InfoCountry
import com.sd.holidays.presentation.ui.screen.LongWeekEnd
import com.sd.holidays.presentation.ui.screen.NextHoliday7Days
import com.sd.holidays.presentation.ui.screen.PublicHoliday
import com.sd.holidays.presentation.ui.theme.HolidaysTheme
import com.sd.holidays.presentation.viewmodel.MainViewModel
import com.sd.holidays.util.sealed.Routes
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

          //      var alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager

                Box(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.Drawer.route
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
                        composable(Routes.Info.route) {
                            InfoCountry(vm, navController)
                        }
                        composable(Routes.LongWeekEnd.route) {
                            LongWeekEnd(vm, navController, keyboardController, focusManager)
                        }
                        composable(Routes.PublicHoliday.route) {
                            PublicHoliday(vm, navController, keyboardController, focusManager)
                        }
                        composable(Routes.NextHoliday7Days.route) {
                            NextHoliday7Days(vm, navController, this@MainActivity)
                        }
                    }
                }
            }
        }
    }
}


