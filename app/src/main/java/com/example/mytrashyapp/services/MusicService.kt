package com.example.mytrashyapp.services

import android.app.*
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.support.v4.media.session.PlaybackStateCompat
import android.telephony.ServiceState
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.mytrashyapp.R
import com.example.mytrashyapp.data.local.preferences.UserPreferences
import com.example.mytrashyapp.ui.MainActivity
import com.example.mytrashyapp.ui.library.screens.songs.models.Song
import com.example.mytrashyapp.util.Constants
import com.example.mytrashyapp.util.Constants.Companion.CHANNEL_ID
import com.example.mytrashyapp.util.Constants.Companion.MUSIC_NOTIFICATION_ID
import com.example.mytrashyapp.util.Constants.Companion.MUS_SERVICE
import com.example.mytrashyapp.util.Constants.Companion.PLAY
import com.example.mytrashyapp.util.Constants.Companion.PLAYER_POS
import com.example.mytrashyapp.util.Constants.Companion.POSITION
import com.example.mytrashyapp.util.Constants.Companion.SET
import com.example.mytrashyapp.util.Constants.Companion.SONGS
import com.example.mytrashyapp.util.Constants.Companion.SONG_ARTIST
import com.example.mytrashyapp.util.Constants.Companion.SONG_NAME
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingCommand
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MusicService : Service() {

    @Inject
    lateinit var preferences: UserPreferences

    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false

    private lateinit var musicPlayer: SimpleExoPlayer
    private lateinit var playerNotificationManager: PlayerNotificationManager

    private lateinit var songs: List<MediaItem>




    override fun onBind(intent: Intent): IBinder? {
        return MusicServiceBinder()
    }

    inner class MusicServiceBinder: Binder() {
        fun getExoPlayerInstance() = musicPlayer
    }

    override fun onCreate() {
        super.onCreate()
        musicPlayer = SimpleExoPlayer.Builder(this).build()

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
                    val intent = Intent(this@MusicService, MainActivity::class.java)
                    intent.action = MUS_SERVICE
                    intent.putExtra(POSITION, musicPlayer.currentWindowIndex)
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

    private suspend fun restoreSavedState() {
        val playerPos = preferences.playerPos.first()
        val songPos = preferences.songPos.first()
        musicPlayer.seekTo(playerPos ?: 0, songPos ?: 0)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent?.action.equals(PLAY)) {
            val extrasList = intent!!.getParcelableArrayListExtra<Song>(SONGS)
            mediaBuilder(extrasList)
            musicPlayer.seekTo(intent.getIntExtra(PLAYER_POS, 0), 0)
            musicPlayer.prepare()
            musicPlayer.play()
        }
        else if(intent?.action.equals(SET) && musicPlayer.mediaItemCount == 0) {
            val extrasList = intent!!.getParcelableArrayListExtra<Song>(SONGS)
            mediaBuilder(extrasList)
            runBlocking { restoreSavedState() }
        }
        return START_STICKY
    }

    private fun mediaBuilder(extrasList:  ArrayList<Song>?) {
        if (extrasList != null) {
            musicPlayer.clearMediaItems()
            songs = extrasList.map { song -> MediaItem.Builder()
                .setUri(song.url)
                .setMediaMetadata(MediaMetadata.Builder()
                    .setTitle(song.name)
                    .setArtist(song.artist)
                    .setDescription("trash music")
                    .build())
                .build() }

            musicPlayer.addMediaItems(songs)
        }
    }

    override fun onDestroy() {
        playerNotificationManager.setPlayer(null)
        musicPlayer.release()
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        if(!musicPlayer.isPlaying) {
            runBlocking {
                preferences.saveSongPos(musicPlayer.currentPosition)
                preferences.savePlayerPos(musicPlayer.currentWindowIndex)}
            stopSelf()
        }
    }


}