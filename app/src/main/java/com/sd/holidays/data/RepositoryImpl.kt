package com.sd.holidays.data

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.sd.holidays.data.api.ApiService
import com.sd.holidays.domain.br.AlarmReceiver
import com.sd.holidays.data.dto.CountryCode
import com.sd.holidays.data.dto.DataHoliday
import com.sd.holidays.data.dto.DataLongWeekEnd
import com.sd.holidays.data.dto.InfoAboutCountry
import com.sd.holidays.domain.Repository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService
): Repository {

    private val prefs = context.getSharedPreferences("notification", Context.MODE_PRIVATE)
    private val stateKey = "state"

    private val alarmManager = context.getSystemService(Activity.ALARM_SERVICE) as AlarmManager

    override fun getStateNotification(): Boolean = prefs.getBoolean(stateKey, false)

    override fun setStateNotification(state: Boolean) {
        with(prefs.edit()) {
            putBoolean(stateKey, state)
            apply()
        }
    }

    override suspend fun loadDataCountry(): List<CountryCode> {
        try {
            val response = apiService.loadAllCountries()
            if (!response.isSuccessful) {
                return listOf()
            }
            val body = response.body()
            return body ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf()
        }
    }

    override suspend fun getInfoAboutCountry(code: String): InfoAboutCountry {
        try {
            val response = apiService.getInfoAboutCountry(code)
            if (!response.isSuccessful) {
                return InfoAboutCountry()
            }
            val body = response.body()
            return body ?: InfoAboutCountry()
        } catch (e: Exception) {
            e.printStackTrace()
            return InfoAboutCountry()
        }
    }

    override suspend fun getLongWeekEnd(year: String, code: String): List<DataLongWeekEnd> {
        try {
            val response = apiService.getLongWeekEnd(year, code)
            if (!response.isSuccessful) {
                return listOf()
            }
            val body = response.body()
            return body ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf()
        }
    }

    override suspend fun getHoliday(year: String, code: String): List<DataHoliday> {
        try {
            val response = apiService.getHoliday(year, code)
            if (!response.isSuccessful) {
                return listOf()
            }
            val body = response.body()
            return body ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf()
        }
    }

    override suspend fun getNextHoliday(): List<DataHoliday> {
        try {
            val response = apiService.getNextHoliday()
            if (!response.isSuccessful) {
                return listOf()
            }
            val body = response.body()
            return body ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf()
        }
    }

    fun setAlarm() {
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
    }

    fun cancelAlarm() {
        val intentA = Intent(context, AlarmReceiver::class.java)
        val pendingIntentA = PendingIntent.getBroadcast(
            context,
            0,
            intentA,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntentA)
    }
}