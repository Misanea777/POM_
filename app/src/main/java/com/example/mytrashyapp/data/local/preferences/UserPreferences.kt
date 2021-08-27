package com.example.mytrashyapp.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.mytrashyapp.ui.library.screens.songs.models.Song
import com.google.android.exoplayer2.MediaItem
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data_store")

class UserPreferences(
        context: Context
) {
    private val applicationContext = context.applicationContext
    val authToken: Flow<String?>
        get() = applicationContext.dataStore.data.map { user_data_store ->
            user_data_store[KEY_AUTH]
        }


    suspend fun saveAuthToken(authToken: String) {
        applicationContext.dataStore.edit { user_data_store ->
            user_data_store[KEY_AUTH] = authToken
        }
    }

    val uiMode: Flow<Boolean>
        get() = applicationContext.dataStore.data.map { user_data_store ->
                user_data_store[UI_MODE] ?: false
            }


    suspend fun saveUIMode(uiMode: Boolean) {
        applicationContext.dataStore.edit { user_data_store ->
            user_data_store[UI_MODE] = uiMode
        }
    }

    suspend fun saveMusicInfo(songsInfo: ArrayList<Song>) {
        applicationContext.dataStore.edit { user_data_store ->
            user_data_store[SONGS_INFO] = Gson().toJson(songsInfo)
        }
    }

    val songsInfo: Flow<List<Song>>
        get() = applicationContext.dataStore.data.map { user_data_store ->
            Gson().fromJson(user_data_store[SONGS_INFO], Array<Song>::class.java).toList()
        }

    suspend fun saveSongPos(songPos: Long) {
        applicationContext.dataStore.edit { user_data_store ->
            user_data_store[SONG_POS] = songPos
        }
    }

    val songPos: Flow<Long?>
        get() = applicationContext.dataStore.data.map { user_data_store ->
            user_data_store[SONG_POS]
        }

    suspend fun savePlayerPos(playerPos: Int) {
        applicationContext.dataStore.edit { user_data_store ->
            user_data_store[PLAYER_STATE] = playerPos
        }
    }

    val playerPos: Flow<Int?>
        get() = applicationContext.dataStore.data.map { user_data_store ->
            user_data_store[PLAYER_STATE]
        }



    companion object{
        private val KEY_AUTH = stringPreferencesKey("key_auth")
        private val UI_MODE = booleanPreferencesKey("ui_mode")
        private val SONGS_INFO = stringPreferencesKey("songs_info")
        private val SONG_POS = longPreferencesKey("song_pos")
        private val PLAYER_STATE = intPreferencesKey("player_state")
    }


}