package com.example.rsshool2021_android_task_pomodoro.features.foregroundservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.rsshool2021_android_task_pomodoro.R
import com.example.rsshool2021_android_task_pomodoro.features.main.MainActivity
import com.example.rsshool2021_android_task_pomodoro.features.timer.ui.*

class ForegroundService : Service() {

    private var isServiceStarted = false
    private var notificationManager: NotificationManager? = null
    private var countDownTimer: CountDownTimer? = null

    private val builder by lazy {
        NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setGroup("Timer")
            .setContentTitle("Timer")
            .setSmallIcon(R.drawable.ic_baseline_access_alarms_24)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(getPendingIntent())
            .setSilent(true)
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        processCommand(intent)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun processCommand(intent: Intent?) {
        when (intent?.extras?.getString(COMMAND_ID) ?: INVALID) {
            COMMAND_START -> {
                val startTime = intent?.extras?.getLong(CURRENT_TIME) ?: return
                commandStart(startTime)
            }
            COMMAND_STOP -> commandStop()
            INVALID -> return
        }
    }

    private fun commandStart(startTime: Long) {
        if (isServiceStarted) {
            return
        }
        Log.i("TAG", "commandStart()")
        try {
            moveToStartedState()
            startForegroundAndNotification()
            continueTimer(startTime)
        } finally {
            isServiceStarted = true
        }
    }

    private fun commandStop() {
        if (!isServiceStarted) {
            return
        }
        try {
            countDownTimer?.cancel()
            stopForeground(true)
            stopSelf()
        } finally {
            isServiceStarted = false
        }
    }

    private fun moveToStartedState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, ForegroundService::class.java))
        } else {
            startService(Intent(this, ForegroundService::class.java))
        }
    }

    private fun startForegroundAndNotification() {
        createChannel()
        val notification = getNotification("content")
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "pomodoro"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                channelName,
                importance
            )
            notificationManager?.createNotificationChannel(notificationChannel)
        }
    }

    private fun continueTimer(startTime: Long) {
        countDownTimer = object : CountDownTimer(startTime - System.currentTimeMillis(), INTERVAL) {

            override fun onTick(millisUntilFinished: Long) {
                notificationManager?.notify(
                    NOTIFICATION_ID,
                    getNotification((startTime - System.currentTimeMillis()).displayTime())
                )
            }

            override fun onFinish() {
                val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val alarm = RingtoneManager.getRingtone(applicationContext, notification)
                alarm.play()
            }
        }.start()
    }

    private fun getNotification(content: String) = builder.setContentText(content).build()

    private fun getPendingIntent(): PendingIntent? {
        val resultIntent = Intent(this, MainActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        return PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT)
    }

    private companion object {
        private const val NOTIFICATION_CHANNEL_ID = "Notification_Channel_Id"
        private const val NOTIFICATION_ID = 777
    }
}
