package com.example.mywidget

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.text.format.DateUtils
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import java.util.*
import android.app.PendingIntent
import android.app.Service
import android.os.IBinder
import android.content.BroadcastReceiver
import android.os.Build

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {

    @SuppressLint("ShortAlarm")
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        var time = DateUtils.formatDateTime(context, Date().time, DateUtils.FORMAT_SHOW_TIME)
        j=time
        time=time.replace("am","")
        time=time.replace("pm","")
        val aj=time.split(":")
        val hour=aj[0]
        var minutes=aj[1]
        minutes=minutes.replace(" ","")
        for (appWidgetId in appWidgetIds) {
                if (hour.length==1){
                    updateAppWidget(context, appWidgetManager, appWidgetId,"0",hour,minutes,"")
                }
                else if(hour.length==2){
                    updateAppWidget(context, appWidgetManager, appWidgetId,hour[0].toString(),hour[1].toString(),minutes,"")
                }
        }
        val intent1 =  Intent(context, MyBroadcast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 100, intent1,0)
        val alarmManager=context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar= Calendar.getInstance()
        calendar.add(Calendar.SECOND,1)
        Log.e("mainActivity","onupdate")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.timeInMillis+i,pendingIntent)
        }
    }


    override fun onEnabled(p: Context) {

    }

    override fun onDisabled(context: Context) {

    }

     class MyBroadcast:BroadcastReceiver(){

        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.e("MyBroadcast","onRecieve")
            val time = DateUtils.formatDateTime(p0!!, Date().time, DateUtils.FORMAT_SHOW_TIME)
            if(time!=j){
                j=time
                i=59000

                Log.e("MyBroadcast","updated")
                updatewidegt(p0!!)
                val intent1 =  Intent(p0, MyBroadcast::class.java);
                val pendingIntent = PendingIntent.getBroadcast(p0, 100, intent1,0)
                val alarmManager=p0.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val calendar= Calendar.getInstance()
                calendar.add(Calendar.SECOND,1)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.timeInMillis+i,pendingIntent)
                }
            }

        }
    }

    companion object {


        var i=1000
        var j=""
        var k=0

        fun updatewidegt(context: Context){
            var time = DateUtils.formatDateTime(context!!, Date().time, DateUtils.FORMAT_SHOW_TIME)
            time = time.replace("am", "")
            time = time.replace("pm", "")
            val aj = time.split(":")
            val hour = aj[0]
            var minutes = aj[1]
            minutes = minutes.replace(" ", "")

            val widget=ComponentName(context,NewAppWidget::class.java)
            val manager=AppWidgetManager.getInstance(context)

            if (hour.length == 1) {
                updateAppWidget1(context, manager, widget, "0", hour, minutes, "")
            } else if (hour.length == 2) {
                updateAppWidget1(
                    context,
                    manager,
                    widget,
                    hour[0].toString(),
                    hour[1].toString(),
                    minutes,
                    ""
                )
            }
        }


        fun BuildUpdate(txttime:String,size:Int,context: Context): Bitmap
        {
            val paint=Paint()
            paint.textSize=size.toFloat()
            val ourCustomType=Typeface.createFromAsset(context.assets,"fonts/mfont.ttf")
            paint.setTypeface(ourCustomType)
            paint.setColor(Color.WHITE)
            paint.textAlign=Paint.Align.LEFT
            paint.isSubpixelText=true
            paint.isAntiAlias=true
            val baseline=-paint.ascent()
            val width=paint.measureText(txttime)+0.5f
            val height=baseline+paint.descent()+0.5f
            val image=Bitmap.createBitmap(width.toInt(),height.toInt(),Bitmap.Config.ARGB_4444)
            val canvas=Canvas(image)
            canvas.drawText(txttime,0f,baseline,paint)
            return image
        }
        fun BuildUpdateBold(txttime:String,size:Int,context: Context): Bitmap
        {
            val paint=Paint()
            paint.textSize=size.toFloat()
            val ourCustomType=Typeface.createFromAsset(context.assets,"fonts/mfontbold.ttf")
            paint.setTypeface(ourCustomType)
            paint.setColor(Color.WHITE)
            paint.textAlign=Paint.Align.LEFT
            paint.isSubpixelText=true
            paint.isAntiAlias=true
            val baseline=-paint.ascent()
            val width=paint.measureText(txttime)
            val height=baseline+4
            val image=Bitmap.createBitmap(width.toInt(),height.toInt(),Bitmap.Config.ARGB_4444)
            val canvas=Canvas(image)
            canvas.drawText(txttime,0f,baseline,paint)
            return image
        }
        fun BuildUpdateRed(txttime:String,size:Int,context: Context): Bitmap
        {
            val paint=Paint()
            paint.textSize=size.toFloat()
            val ourCustomType=Typeface.createFromAsset(context.assets,"fonts/mfontbold.ttf")
            paint.setTypeface(ourCustomType)
            paint.setColor(Color.parseColor("#CA0000"))
            paint.textAlign=Paint.Align.LEFT
            paint.isSubpixelText=true
            paint.isAntiAlias=true
            val baseline=-paint.ascent()
            val width=paint.measureText(txttime)
            val height=baseline+4
            val image=Bitmap.createBitmap(width.toInt(),height.toInt(),Bitmap.Config.ARGB_4444)
            val canvas=Canvas(image)
            canvas.drawText(txttime,0f,baseline,paint)
            return image
        }


         internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int,hour1:String,hour2:String,minutes:String,date:String
        ) {
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)
             views.setImageViewBitmap(R.id.imgtimehour1, BuildUpdateRed(hour1,200,context))
             views.setImageViewBitmap(R.id.imgtimehour2, BuildUpdateBold(hour2,200,context))
             views.setImageViewBitmap(R.id.imgtimeminutes, BuildUpdateBold(minutes,200,context))
             views.setImageViewBitmap(R.id.imgdate, BuildUpdate("Tue, Nov 28",45,context))

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
        internal fun updateAppWidget1(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: ComponentName,hour1:String,hour2:String,minutes:String,date:String
        ) {
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)
            views.setImageViewBitmap(R.id.imgtimehour1, BuildUpdateRed(hour1,200,context))
            views.setImageViewBitmap(R.id.imgtimehour2, BuildUpdateBold(hour2,200,context))
            views.setImageViewBitmap(R.id.imgtimeminutes, BuildUpdateBold(minutes,200,context))
            views.setImageViewBitmap(R.id.imgdate, BuildUpdate("Mon, Mar 30",45,context))

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

