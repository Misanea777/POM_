package com.example.mytrashyapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.mytrashyapp.R
import com.example.mytrashyapp.data.preferences.UserPreferences
import kotlinx.coroutines.launch

class SettingsFragment : PreferenceFragmentCompat() {

    lateinit var pref: UserPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        pref = UserPreferences(requireContext())

        setLightModeListener()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun setLightModeListener() {
        val uiPreference = preferenceManager.findPreference<Preference>("uiMode") as SwitchPreferenceCompat

        pref.uiMode.asLiveData().observe(viewLifecycleOwner, Observer {
            uiPreference.setChecked(it)

            if(it) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }
        })

        uiPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener {preference, newValue ->
            lifecycleScope.launch {
                pref.saveUIMode(newValue as Boolean)
            }

            false
        }


    }



}