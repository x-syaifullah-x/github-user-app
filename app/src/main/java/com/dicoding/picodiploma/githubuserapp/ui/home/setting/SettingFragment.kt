package com.dicoding.picodiploma.githubuserapp.ui.home.setting

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.reminder.DailyReminder

class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.xml_preferences, rootKey)

        findPreference<Preference>(getString(R.string.preferences_key_daily_reminder))
            ?.setOnPreferenceChangeListener { preference, newValue ->
                DailyReminder().setRepeatingAlarm(preference.context, newValue as Boolean)
                true
            }
    }
}