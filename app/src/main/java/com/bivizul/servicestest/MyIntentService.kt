package com.bivizul.servicestest

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyIntentService : IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(false)  // аналог true-START_NOT_STICKY, false-START_REDELIVER_INTENT
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    // автоматически выполняется в другом потоке
    override fun onHandleIntent(p0: Intent?) {
        log("onHandleIntent")
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("Timer $i")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyForegroundService: $message")
    }

    private fun createNotificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Title")    // устанавливаем заголовок
        .setContentText("Text")     // установим текст
        .setSmallIcon(R.drawable.ic_launcher_background)    // установим иконку
        .build()

    companion object {

        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_name"
        private const val NOTIFICATION_ID = 1
        private const val NAME = "MyIntentService"

        fun newIntent(context: Context): Intent {
            return Intent(context, MyIntentService::class.java)
        }
    }
}