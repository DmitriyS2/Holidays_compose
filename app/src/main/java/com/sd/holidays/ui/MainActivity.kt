package com.sd.holidays.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
            Drawer(
                context = this,
          //      listCountries,
              //  viewModel,
               )
            }
        }
    }
}
