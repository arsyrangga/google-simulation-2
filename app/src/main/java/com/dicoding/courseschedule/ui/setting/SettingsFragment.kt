package com.dicoding.courseschedule.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder

class SettingsFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        val themePreference = findPreference<ListPreference>("key_dark_mode")
        themePreference?.setOnPreferenceChangeListener { _, newValue ->

            val nightMode = when (newValue) {
                "off" -> AppCompatDelegate.MODE_NIGHT_NO
                "on" -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            updateTheme(nightMode)
        }
        // TODO 11: Schedule and cancel notification in DailyReminder based on SwitchPreference
        val notificationSwitch = findPreference<SwitchPreference>("key_notification")
        notificationSwitch?.setOnPreferenceChangeListener { _, newValue ->
            val dailyReminder = DailyReminder()
            val context = requireContext()
            if(newValue as Boolean){
                dailyReminder.setDailyReminder(context)
            }else{
                dailyReminder.cancelAlarm(context)
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}