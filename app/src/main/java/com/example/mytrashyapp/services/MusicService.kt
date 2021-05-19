package com.example.mytrashyapp.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import android.support.v4.media.session.PlaybackStateCompat
import android.telephony.ServiceState
import android.widget.Toast
import java.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingCommand


class MusicService : Service() {

    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false

    override fun onBind(intent: Intent): IBinder? {
        println("Some component want to bind with the service")
        // We don't provide binding, so return null
        return null
    }


}