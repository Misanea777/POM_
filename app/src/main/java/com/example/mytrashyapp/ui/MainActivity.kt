package com.example.mytrashyapp.ui


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.*
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
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
import com.example.mytrashyapp.util.Constants.Companion.CHANNEL_ID
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.util.RepeatModeUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var playerControlView: PlayerControlView

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var connection: ServiceConnection


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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, "Music_channel", NotificationManager.IMPORTANCE_HIGH)
            getSystemService(NotificationManager::class.java).createNotificationChannel(notificationChannel)
        }


        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            runBlocking { scanForAudioFiles() }
        }

        initConection()

        bindService(Intent(this, MusicService::class.java), connection, Context.BIND_ABOVE_CLIENT)


        playerControlView = binding.player

        playerControlView.showTimeoutMs = 0
        playerControlView.repeatToggleModes = RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE or RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL


    }

    fun initConection() {
        connection = object: ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                if(service is MusicService.MusicServiceBinder) {
                    playerControlView.player = service.getExoPlayerInstance()
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
                val audioTitle:String = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)).substringBeforeLast(".")
                val audioUrl = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))

                // Add the current music to the list
                list.add(Song(audioTitle, audioArtist, audioUrl))
            }while (cursor.moveToNext())
        }
        return  list

    }

    override fun onDestroy() {
        super.onDestroy()

    }


}