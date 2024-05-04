package com.example.skydash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnPlay: ImageView = findViewById(R.id.btn_play)
        val btnHighScore: ImageView = findViewById(R.id.btn_high_score)
        val btnClose: ImageView = findViewById(R.id.btn_close)

        btnPlay.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnHighScore.setOnClickListener {
            val intent = Intent(this, ScoreActivity::class.java)
            startActivity(intent)
        }

        btnClose.setOnClickListener {
            finish()
        }
    }
}