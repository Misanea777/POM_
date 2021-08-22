package com.example.mytrashyapp.services

import android.app.PendingIntent
import android.graphics.Bitmap
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class DescriptionAdapter: PlayerNotificationManager.MediaDescriptionAdapter {
    override fun getCurrentContentTitle(player: Player): CharSequence {
        return "Test"
    }

    override fun createCurrentContentIntent(player: Player): PendingIntent? {
        return null

    }

    override fun getCurrentContentText(player: Player): CharSequence? {
        return "Test"
    }

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        return null
    }
}