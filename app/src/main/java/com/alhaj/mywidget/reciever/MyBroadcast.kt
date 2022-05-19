package com.alhaj.mywidget.reciever

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.alhaj.mywidget.utils.UPDATE_SEONCDS
import com.alhaj.mywidget.utils.getPendingIntent
import com.alhaj.mywidget.utils.updateWidget
import java.util.*


class MyBroadcast : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {

        Log.e("MyBroadcast", "update receive")

        p0?.let { updateWidget(it) }

        val intent1 = Intent(p0, MyBroadcast::class.java)

        val pendingIntent = p0?.let { getPendingIntent(it, 32744, intent1) }

        val alarmManager = p0?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, UPDATE_SEONCDS)

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
}