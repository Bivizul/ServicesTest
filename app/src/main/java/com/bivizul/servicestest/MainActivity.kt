package com.bivizul.servicestest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bivizul.servicestest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.simpleService.setOnClickListener {
            // стоп сервиса из вне
            stopService(MyForegroundService.newIntent(this))
            startService(MyService.newIntent(this, 25))
        }
        binding.foregroundService.setOnClickListener {
            ContextCompat.startForegroundService(   // ContentCompat проверяет API >= 26
                this,
                MyForegroundService.newIntent(this)
            )
        }
        binding.intentService.setOnClickListener {
            ContextCompat.startForegroundService(   // ContentCompat проверяет API >= 26
                this,
                MyIntentService.newIntent(this)
            )
        }
    }
}