package com.dicoding.picodiploma.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.databinding.ActivityMainBinding
import com.dicoding.picodiploma.githubuserapp.reminder.DailyReminder
import com.dicoding.picodiploma.githubuserapp.ui.home.setting.SettingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment).navController
        setupWithNavController(binding.bottomNavigationView, navController)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.fragment_user,
            R.id.fragment_favorite
        )
        setupActionBarWithNavController(navController, appBarConfiguration.build())

        val pref = getSharedPreferences("FIRST_TIME", MODE_PRIVATE)
        if (pref.getBoolean("FIRST_TIME", true)) {
            DailyReminder().setRepeatingAlarm(this, true)
            pref.edit().putBoolean("FIRST_TIME", false).apply()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.settings_menu -> {
            startActivity(Intent(this, SettingActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}