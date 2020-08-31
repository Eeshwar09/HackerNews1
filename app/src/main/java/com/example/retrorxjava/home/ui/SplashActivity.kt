@file:Suppress("DEPRECATION")

package com.example.retrorxjava.home.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.retrorxjava.R
import android.content.Intent
import android.animation.ObjectAnimator
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.toolbar.*


class SplashActivity : AppCompatActivity() {

    private var plashTime = 4000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        playProgress()
        toolbar1.title = getString(R.string.app_name)


        Handler().postDelayed({
            val mySuperIntent = Intent(this, HomeActivity::class.java)
            startActivity(mySuperIntent)
            finish()
        }, plashTime.toLong())

    }

    private fun playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 100)
            .setDuration(4000)
            .start()
    }
}
