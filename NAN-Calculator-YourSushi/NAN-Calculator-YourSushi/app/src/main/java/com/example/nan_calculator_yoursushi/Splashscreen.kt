package com.example.nan_calculator_yoursushi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Splashscreen : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splashscreen)

            val videoView = findViewById<VideoView>(R.id.videoView)
            val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.splash_screen}")
            videoView.setVideoURI(videoUri)

            videoView.setOnCompletionListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

            videoView.start()
        }
    }
