package com.dicoding.picodiploma.githubuserapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(500)
        Intent(this@SplashActivity, MainActivity::class.java).apply {
            startActivity(this); finish()
        }
    }
}