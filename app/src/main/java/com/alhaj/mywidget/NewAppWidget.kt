package com.alhaj.mywidget

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*
import android.os.Build
import com.alhaj.mywidget.reciever.MyBroadcast
import com.alhaj.mywidget.utils.getPendingIntent
import com.alhaj.mywidget.utils.toString
import com.alhaj.mywidget.utils.updateAppWidgetWithId

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {

    @SuppressLint("ShortAlarm")
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        val currentDate = Date()
        val time = currentDate.toString("hh:mm")
        val date = currentDate.toString("EEE, MMM dd")
        val aj = time.split(":")

        val hour = aj[0]
        var minutes = aj[1]

        minutes = minutes.replace(" ", "")

        for (appWidgetId in appWidgetIds) {

            var finalHour1: String
            var finalHour2: String

            if (hour.length == 1) {
                finalHour1 = "0"
                finalHour2 = hour
            } else {
                finalHour1 = hour[0].toString()
                finalHour2 = hour[1].toString()

            }

            updateAppWidgetWithId(
                context,
                appWidgetManager,
                appWidgetId,
                finalHour1,
                finalHour2,
                minutes,
                date
            )

        }
        val intent1 = Intent(context, MyBroadcast::class.java)

        val pendingIntent = getPendingIntent(context, 32744, intent1)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance()

        val remainingSecondInNextMinute = 60 - calendar.get(Calendar.SECOND)

        calendar.add(Calendar.SECOND,remainingSecondInNextMinute)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
                Log.e("MyBroadcast", "updated")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            try {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
                Log.e("MyBroadcast", "updated")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    override fun onEnabled(p: Context) {

    }

    override fun onDisabled(context: Context) {

    }

}



