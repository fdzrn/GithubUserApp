package com.codelab.githubuser.view.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.codelab.githubuser.R
import com.codelab.githubuser.broadcast.AlarmReceiver
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class SettingsActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tool_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_activity -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            R.id.settings_activity -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {
        private lateinit var alarmReceiver: AlarmReceiver
        private lateinit var switchPreferenceCompat: SwitchPreferenceCompat
        private lateinit var reminderKey: String

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            alarmReceiver = AlarmReceiver()
            reminderInitialize()
            statusNotification()
        }

        override fun onSharedPreferenceChanged(sharedPref: SharedPreferences, key: String) {
            if (key == reminderKey) {
                switchPreferenceCompat.isChecked = sharedPref.getBoolean(reminderKey, false)
            }

            val state: Boolean = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(reminderKey, false)
            setStatusNotification(state)
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        private fun reminderInitialize() {
            reminderKey = getString(R.string.reminder_key)
            switchPreferenceCompat =
                findPreference<SwitchPreferenceCompat>(reminderKey) as SwitchPreferenceCompat
        }

        private fun statusNotification() {
            val sharedPref = preferenceManager.sharedPreferences
            switchPreferenceCompat.isChecked = sharedPref.getBoolean(reminderKey, false)
        }

        private fun setStatusNotification(state: Boolean) {
            if (state) context?.let { alarmReceiver.setNotificationON(it) }
            else context?.let { alarmReceiver.setNotificationOFF(it) }
        }
    }
}