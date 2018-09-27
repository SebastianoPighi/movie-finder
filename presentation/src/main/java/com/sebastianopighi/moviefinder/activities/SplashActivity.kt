package com.sebastianopighi.moviefinder.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sebastianopighi.moviefinder.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val moviesIntent = Intent(this, MoviesActivity::class.java)
        startActivity(moviesIntent)
        finish()
    }
}
