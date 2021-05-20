package com.dicoding.picodiploma.githubuserapp.ui.home.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.dicoding.picodiploma.githubuserapp.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.apply { setDisplayHomeAsUpEnabled(true) }

        supportFragmentManager.commit {
            replace(R.id.setting, SettingFragment())
        }
    }
}