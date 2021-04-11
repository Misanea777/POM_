package com.example.mytrashyapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class UserPreferences(
    context: Context
) {
    private val applicationContext = context.applicationContext
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data_store")
    val authToken: Flow<String?>
        get() = applicationContext.dataStore.data.map { user_data_store ->
            user_data_store[KEY_AUTH]
        }


    suspend fun saveAuthToken(authToken: String) {
        applicationContext.dataStore.edit { user_data_store ->
            user_data_store[KEY_AUTH] = authToken
        }
    }

    companion object{
        private val KEY_AUTH = stringPreferencesKey("kwy_auth")
    }


}