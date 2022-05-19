package com.alhaj.mywidget.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.text.SimpleDateFormat
import java.util.*

fun Date?.toString(outputSDF: String): String {
    if (this == null)
        return "null"
    return SimpleDateFormat(outputSDF, Locale.getDefault()).format(this)
}

fun getPendingIntent(context: Context, id: Int, intent: Intent): PendingIntent {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    } else {
        PendingIntent.getBroadcast(context, id, intent, 0)
    }
}