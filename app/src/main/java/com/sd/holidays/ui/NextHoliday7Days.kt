package com.sd.holidays.ui

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sd.holidays.AlarmReceiver
import com.sd.holidays.util.getDay
import com.sd.holidays.viewmodel.MainViewModel
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NextHoliday7Days(vm: MainViewModel = viewModel(), navController: NavController, context: Context) {
    val nextHoliday by vm.dataModelNextHoliday.observeAsState()

    val checkedState = remember { mutableStateOf(false) }
    val textColor = remember { mutableStateOf(Color.Unspecified) }

    val alarmManager = context.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
//    val alarmIntent: PendingIntent = Intent(context, AlarmReceiver::class.java).let { intent->
//        //     intent.putExtra("key", "hello")
//        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
  //  }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        //     verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(text = "Ближайшие праздники")
            },

            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("drawer")
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "backFromNextHoliday7Days"
                    )
                }
            }
        )
        when {
            nextHoliday?.loading == true -> {
                Log.d("MyLog", "nextHol loading")
            }

            nextHoliday?.error == true -> {
                Log.d("MyLog", "nextHol error")
            }

            else -> {
                Row (verticalAlignment = Alignment.CenterVertically){
                    Text("Уведомления о праздниках", fontSize = 16.sp, color = textColor.value, modifier = Modifier.padding(8.dp, 0.dp))
                    Switch(
                        checked = checkedState.value,
                        onCheckedChange = {
                            checkedState.value = it
                            if(checkedState.value) {
                                textColor.value = Color(0xFF6650a4)

                                val data = LocalDate.now().atTime(10,0).toInstant(ZoneOffset.UTC).toEpochMilli()
                                val alarmIntent: PendingIntent =
                                    Intent(context, AlarmReceiver::class.java).let { intent ->
                                        PendingIntent.getBroadcast(
                                            context,
                                            0,
                                            intent,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                        )
                                    }
                                alarmManager.setInexactRepeating(
                                    AlarmManager.RTC_WAKEUP,
                                    data,
                                       AlarmManager.INTERVAL_DAY,
                                    //     AlarmManager.INTERVAL_HOUR,
                               //     30000,
                                    alarmIntent
                                )
                                Toast.makeText(context, "Уведомления включены", Toast.LENGTH_SHORT).show()
                                Log.d("MyLog", "alarmAManager from switch on=$alarmManager")
                            } else {
                                textColor.value = Color.Unspecified
                                Toast.makeText(context, "Уведомления выключены", Toast.LENGTH_SHORT).show()
                                val intentA = Intent(context, AlarmReceiver::class.java)
                                val pendingIntentA = PendingIntent.getBroadcast(
                                    context,
                                    0,
                                    intentA,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                                )
                                alarmManager.cancel(pendingIntentA)
                                Log.d("MyLog", "alarmAManager from switch off=$alarmManager, pendingIntentA=$pendingIntentA")
                            }
                        }
                    )
                }
                Log.d("MyLog", "nextHol else")
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn() {
                    itemsIndexed(nextHoliday?.listDataHoliday ?: emptyList()) { _, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clickable {

                                }
                        ) {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Страна: ${item.countryCode}", fontSize = 18.sp
                            )
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Дата: ${item.date.getDay()}"
                            )
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Местное название: ${item.localName}"
                            )
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Название: ${item.name}"
                            )
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "Тип: ${item.types.joinToString(", ")}"
                            )
                        }
                    }
                }
            }
        }
    }
}