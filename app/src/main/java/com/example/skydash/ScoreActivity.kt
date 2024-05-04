package com.example.skydash

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val sp = getSharedPreferences("gameSetting", Context.MODE_PRIVATE)
        val bestscore = sp.getInt("bestScore", 0)
        val lastScore = sp.getInt("lastScore", 0)

        val highestScoreTextView = findViewById<TextView>(R.id.highestScoreTextView)
        val lastScoreTextView = findViewById<TextView>(R.id.lastScoreTextView)

        highestScoreTextView.text = "Best: $bestscore"
        lastScoreTextView.text = "Last Score: $lastScore"

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }
}