package com.example.mytrashyapp.ui


import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.app.NotificationCompat.PRIORITY_LOW
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mytrashyapp.R
import com.example.mytrashyapp.data.local.preferences.UserPreferences
import com.example.mytrashyapp.databinding.ActivityMainBinding
import com.example.mytrashyapp.services.MusicService
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.StyledPlayerControlView
import com.google.android.exoplayer2.util.RepeatModeUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var preferences: UserPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        lifecycleScope.launch { //cashing the uiMode if possible
            preferences.uiMode.first()  // however the next thing is setupUI which is runBlocking so it doesnt matter that much
        }

        setupUI()


        setContentView(binding.root)

        navController = findNavController(R.id.fragment)
        drawerLayout = binding.drawerLayout
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        binding.navigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //Testing

        startStopService()

//        val res = Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE).authority(packageName).path(R.raw.test.toString()).build()
//
//        var player = SimpleExoPlayer.Builder(this).build()
//
//        var playerView: PlayerControlView = binding.player
//
//        playerView.player = player
//
//
//        val mi = MediaItem.fromUri(res)
//        player.setMediaItem(mi)
//        player.setPlaybackSpeed(1F)
//        player.prepare()
//        playerView.showTimeoutMs = 0
//        playerView.repeatToggleModes = RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE or RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL

    }

    private fun startStopService() {
        startService(Intent(this, MusicService::class.java))
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun setupUI() {
        val lightMode = runBlocking { preferences.uiMode.first() }
        println("suca $lightMode")
        if(lightMode) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        }

    }



    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            println("landscape")
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            println("portrait")
        }
    }



}