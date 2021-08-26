package com.example.mytrashyapp.ui


import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.app.ActivityCompat
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
import com.example.mytrashyapp.ui.library.screens.songs.models.Song
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.StyledPlayerControlView
import com.google.android.exoplayer2.util.RepeatModeUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.jar.Manifest
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

//        startStopService()


        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 111)
        } else {
            runBlocking { scanForAudioFiles() }
        }





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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            runBlocking { scanForAudioFiles() }
        }
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


    suspend fun scanForAudioFiles() {
        preferences.saveMusicInfo(musicFiles() as ArrayList<Song>)

//        Toast.makeText(applicationContext,localMusic.size.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun Context.musicFiles():MutableList<Song>{
        val list:MutableList<Song> = mutableListOf()
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor: Cursor? = this.contentResolver.query(
            uri,
            null,
            selection,
            null,
            sortOrder
        )

        if (cursor!= null && cursor.moveToFirst()){
            val id:Int = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val title:Int = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)

            do {
                val audioArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val audioTitle:String = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                val audioUrl = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))

                // Add the current music to the list
                list.add(Song(audioTitle, audioArtist, audioUrl))
            }while (cursor.moveToNext())
        }
        return  list

    }


}