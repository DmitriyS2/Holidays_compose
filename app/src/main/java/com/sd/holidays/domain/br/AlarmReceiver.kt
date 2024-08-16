package com.sd.holidays.domain.br

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.sd.holidays.R
import com.sd.holidays.domain.Repository
import com.sd.holidays.presentation.ui.MainActivity
import com.sd.holidays.util.getEnd
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: Repository

    override fun onReceive(context: Context, intent: Intent) {

        Log.d("MyLog", "onReceive")

        CoroutineScope(Dispatchers.Default).launch {
            val dateNow = LocalDate.now()
            var count = repository.getNextHoliday().map {
                LocalDate.parse(it.date) == dateNow
            }.count {
                it
            }
            if(count==0) {
                Log.d("MyLog", "pause because count=0")
                delay(120000)
                Log.d("MyLog", "continue to receive count")
                count = repository.getNextHoliday().map {
                    LocalDate.parse(it.date) == dateNow
                }.count {
                    it
                }
            }
            Log.d("MyLog", "dateNow==$count")
            buildNotification(context, count)
        }
    }

    private fun buildNotification(context: Context, count:Int) {
        val CHANNEL_ID = "channelID"
        val CHANNEL_NAME = "channelName"
        val title = if(count==0) "Сегодня нет праздника" else "Сегодня праздник"
        val text = getEnd(count)

        val intentActivity = Intent(context, MainActivity::class.java)
        val pi =
            PendingIntent.getActivity(context, 0, intentActivity, PendingIntent.FLAG_IMMUTABLE)
        val vibrate: LongArray = longArrayOf(500L, 500L, 500L)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.holidays)
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(pi)
            .setAutoCancel(true)
            .setVibrate(vibrate)
            .setShowWhen(true) // показывать время в уведомлении
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        if (
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(Random.nextInt(100_000), notification)
        }
    }
}