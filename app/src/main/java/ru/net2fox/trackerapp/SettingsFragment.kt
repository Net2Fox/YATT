package ru.net2fox.trackerapp

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            if (prefs.getString("dark_theme", "").toString() == "system") {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            } else if (prefs.getString("dark_theme", "").toString() == "on") {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(listener)
    }
}