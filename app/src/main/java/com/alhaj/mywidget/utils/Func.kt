package com.alhaj.mywidget.utils

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.graphics.*
import android.widget.RemoteViews
import com.alhaj.mywidget.NewAppWidget
import com.alhaj.mywidget.R
import java.util.*


fun updateWidget(context: Context) {
    val currentDate = Date()

    val time = currentDate.toString("hh:mm")
    val date = currentDate.toString("EEE, MMM dd")

    val aj = time.split(":")

    val hour = aj[0]
    var minutes = aj[1]
    minutes = minutes.replace(" ", "")

    val widget = ComponentName(context, NewAppWidget::class.java)
    val manager = AppWidgetManager.getInstance(context)

    val finalHour1: String
    val finalHour2: String

    if (hour.length == 1) {
        finalHour1 = "0"
        finalHour2 = hour
    } else {
        finalHour1 = hour[0].toString()
        finalHour2 = hour[1].toString()

    }

    updateAppWidgetWithName(
        context,
        manager,
        widget,
        finalHour1,
        finalHour2,
        minutes,
        date
    )
}


fun buildUpdate(txttime: String, size: Int, context: Context): Bitmap {
    val paint = Paint()
    paint.textSize = size.toFloat()
    val ourCustomType = Typeface.createFromAsset(context.assets, "fonts/mfont.ttf")
    paint.setTypeface(ourCustomType)
    paint.setColor(Color.WHITE)
    paint.textAlign = Paint.Align.LEFT
    paint.isSubpixelText = true
    paint.isAntiAlias = true
    val baseline = -paint.ascent()
    val width = paint.measureText(txttime) + 0.5f
    val height = baseline + paint.descent() + 0.5f
    val image = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_4444)
    val canvas = Canvas(image)
    canvas.drawText(txttime, 0f, baseline, paint)
    return image
}

fun buildUpdateBold(txttime: String, size: Int, context: Context): Bitmap {
    val paint = Paint()
    paint.textSize = size.toFloat()
    val ourCustomType = Typeface.createFromAsset(context.assets, "fonts/mfontbold.ttf")
    paint.setTypeface(ourCustomType)
    paint.setColor(Color.WHITE)
    paint.textAlign = Paint.Align.LEFT
    paint.isSubpixelText = true
    paint.isAntiAlias = true
    val baseline = -paint.ascent()
    val width = paint.measureText(txttime)
    val height = baseline + 4
    val image = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_4444)
    val canvas = Canvas(image)
    canvas.drawText(txttime, 0f, baseline, paint)
    return image
}

fun buildUpdateRed(txttime: String, size: Int, context: Context): Bitmap {
    val paint = Paint()
    paint.textSize = size.toFloat()
    val ourCustomType = Typeface.createFromAsset(context.assets, "fonts/mfontbold.ttf")
    paint.setTypeface(ourCustomType)
    paint.setColor(Color.parseColor("#CA0000"))
    paint.textAlign = Paint.Align.LEFT
    paint.isSubpixelText = true
    paint.isAntiAlias = true
    val baseline = -paint.ascent()
    val width = paint.measureText(txttime)
    val height = baseline + 4
    val image = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_4444)
    val canvas = Canvas(image)
    canvas.drawText(txttime, 0f, baseline, paint)
    return image
}


fun updateAppWidgetWithId(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    hour1: String,
    hour2: String,
    minutes: String,
    date: String
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.new_app_widget)
    views.setImageViewBitmap(R.id.imgtimehour1, buildUpdateRed(hour1, 200, context))
    views.setImageViewBitmap(R.id.imgtimehour2, buildUpdateBold(hour2, 200, context))
    views.setImageViewBitmap(R.id.imgtimeminutes, buildUpdateBold(minutes, 200, context))
    views.setImageViewBitmap(R.id.imgdate, buildUpdate(date, 45, context))

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

fun updateAppWidgetWithName(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: ComponentName,
    hour1: String,
    hour2: String,
    minutes: String,
    date: String
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.new_app_widget)
    views.setImageViewBitmap(R.id.imgtimehour1, buildUpdateRed(hour1, 200, context))
    views.setImageViewBitmap(R.id.imgtimehour2, buildUpdateBold(hour2, 200, context))
    views.setImageViewBitmap(R.id.imgtimeminutes, buildUpdateBold(minutes, 200, context))
    views.setImageViewBitmap(R.id.imgdate, buildUpdate(date, 45, context))

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}