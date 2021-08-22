package com.example.mytrashyapp.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.support.v4.media.session.PlaybackStateCompat
import android.telephony.ServiceState
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.mytrashyapp.R
import com.example.mytrashyapp.ui.MainActivity
import com.example.mytrashyapp.util.Constants.Companion.CHANNEL_ID
import com.example.mytrashyapp.util.Constants.Companion.MUSIC_NOTIFICATION_ID
import com.google.android.exoplayer2.SimpleExoPlayer
import java.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingCommand

//aloha
class MusicService : Service() {

    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false

    private lateinit var musicPlayer: SimpleExoPlayer




    override fun onBind(intent: Intent): IBinder? {
        println("Some component want to bind with the service")
        // We don't provide binding, so return null
        return null
    }

    override fun onCreate() {
        super.onCreate()
        initMusic()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) showNotification()

//        if(musicPlayer.isPlaying) {
//            musicPlayer.stop()
//        }else {
//            musicPlayer.start()
//        }

        musicPlayer.start()


        return START_STICKY
//        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = Notification
            .Builder(this, CHANNEL_ID)
            .setContentText("Music Player")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(MUSIC_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Music Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun initMusic() {
        musicPlayer = MediaPlayer.create(this, R.raw.test)
        musicPlayer.setVolume(100F, 100F)
    }
}