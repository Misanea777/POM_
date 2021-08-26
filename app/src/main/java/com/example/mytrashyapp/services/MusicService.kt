package com.example.mytrashyapp.services

import android.app.*
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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
import com.example.mytrashyapp.util.Constants.Companion.PLAY
import com.example.mytrashyapp.util.Constants.Companion.SONG_ARTIST
import com.example.mytrashyapp.util.Constants.Companion.SONG_NAME
import com.google.android.exoplayer2.*
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
        musicPlayer.prepare()
        musicPlayer.playWhenReady = true

        playerNotificationManager = PlayerNotificationManager
            .Builder(
                this,
                MUSIC_NOTIFICATION_ID,
                CHANNEL_ID
            )
            .setMediaDescriptionAdapter(object: PlayerNotificationManager.MediaDescriptionAdapter{
                override fun getCurrentContentText(player: Player): CharSequence? {
                    return player.mediaMetadata.description
                }

                override fun getCurrentContentTitle(player: Player): CharSequence {
                    return player.mediaMetadata.title.toString()
                }

                override fun getCurrentLargeIcon(
                    player: Player,
                    callback: PlayerNotificationManager.BitmapCallback
                ): Bitmap? {
                    return null
                }

                override fun createCurrentContentIntent(player: Player): PendingIntent? {
                    // return pending intent
                    val intent = Intent(this@MusicService, MainActivity::class.java);
                    return PendingIntent.getActivity(
                        this@MusicService, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }
            })
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
        playerNotificationManager.setColor(0x997300)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent?.action.equals(PLAY)) {
            val extras = intent!!.extras
            val mediaItem = MediaItem.Builder()
                .setUri(intent.data!!)
                .setMediaMetadata(MediaMetadata.Builder()
                    .setTitle(extras?.getString(SONG_NAME))
                    .setArtist(extras?.getString(SONG_ARTIST))
                    .setDescription("trash music")
                    .build())
                .build()
            musicPlayer.setMediaItem(mediaItem)
        }
        return START_STICKY
    }

    override fun onDestroy() {
        playerNotificationManager.setPlayer(null)
        musicPlayer.release()
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
//        stopSelf()
    }


}