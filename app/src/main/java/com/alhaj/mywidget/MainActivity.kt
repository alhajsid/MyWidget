package com.alhaj.mywidget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.ComponentName
import android.appwidget.AppWidgetManager
import android.os.Build
import android.widget.Toast
import com.alhaj.mywidget.utils.getPendingIntent


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            addWidgetToHomeScreen()
        }

    }

    private fun addWidgetToHomeScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mAppWidgetManager = getSystemService(AppWidgetManager::class.java)
            val myProvider = ComponentName(this, NewAppWidget::class.java)
            val b = Bundle()
            b.putString("ggg", "ggg")
            if (mAppWidgetManager.isRequestPinAppWidgetSupported) {
                val pinnedWidgetCallbackIntent =
                    Intent(this, NewAppWidget::class.java)
                val successCallback = getPendingIntent(this,0,pinnedWidgetCallbackIntent)
                mAppWidgetManager.requestPinAppWidget(myProvider, b, successCallback)
            }
        }else{
            Toast.makeText(this,"Go to home screen and add widget manually, Android 7 and lower doesn't support",Toast.LENGTH_SHORT).show()
        }
    }
}

