package com.example.mytrashyapp.services

import android.app.*
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
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
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import java.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingCommand

//aloha
class MusicService : Service() {

    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false

    private lateinit var musicPlayer: SimpleExoPlayer
    private lateinit var playerNotificationManager: PlayerNotificationManager




    override fun onBind(intent: Intent): IBinder? {
        println("Some component want to bind with the service")
        // We don't provide binding, so return null
        return null
    }

    override fun onCreate() {
        super.onCreate()
        musicPlayer = SimpleExoPlayer.Builder(this).build()
        //
        val res = Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE).authority(packageName).path(R.raw.test.toString()).build()
        val mi = MediaItem.fromUri(res)
        musicPlayer.setMediaItem(mi)
        musicPlayer.prepare()
        musicPlayer.playWhenReady = true
        //

        playerNotificationManager = PlayerNotificationManager
            .Builder(
                this,
                MUSIC_NOTIFICATION_ID,
                CHANNEL_ID
            )
            .setMediaDescriptionAdapter(DescriptionAdapter())
            .setNotificationListener(object : PlayerNotificationManager.NotificationListener{
                override fun onNotificationPosted(
                    notificationId: Int,
                    notification: Notification,
                    ongoing: Boolean
                ) {
                    startForeground(notificationId, notification)
                }

                override fun onNotificationCancelled(
                    notificationId: Int,
                    dismissedByUser: Boolean
                ) {
                    stopSelf()
                }
            })
            .build()

        playerNotificationManager.setPlayer(musicPlayer)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return START_STICKY
    }


}