package com.example.skydash

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var txt_score: TextView
        lateinit var txt_best_score: TextView
        lateinit var txt_score_over: TextView
        lateinit var rl_game_over: RelativeLayout
        lateinit var btn_start: Button
    }

    private lateinit var gv: GameView
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var icCloseImageView:ImageView
    private lateinit var btnPause:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        Constants.SCREEN_WIDTH = dm.widthPixels
        Constants.SCREEN_HEIGHT = dm.heightPixels
        setContentView(R.layout.activity_main)
        txt_score = findViewById(R.id.text_score)
        txt_best_score = findViewById(R.id.txt_best_score)
        txt_score_over = findViewById(R.id.txt_score_over)
        rl_game_over = findViewById(R.id.rl_game_over)
        btn_start = findViewById(R.id.btn_start)
        gv = findViewById(R.id.gv)
        icCloseImageView = findViewById<ImageView>(R.id.btn_close)
        btnPause = findViewById(R.id.btn_pause)

        mediaPlayer = MediaPlayer.create(this, R.raw.sillychipsong)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        btn_start.setOnClickListener {
            gv.setStart(true)
            txt_score.visibility = View.VISIBLE
            btn_start.visibility = View.INVISIBLE
        }

        icCloseImageView.setOnClickListener {
            finish()
        }

        rl_game_over.setOnClickListener {
            btn_start.visibility = View.VISIBLE
            rl_game_over.visibility = View.INVISIBLE
            gv.setStart(false)
            gv.reset()
        }

        btnPause.setOnClickListener {
            pauseGame()
        }

    }

    private fun pauseGame() {
        gv.setStart(false)
        btnPause.isEnabled = false // Disable pause button while the game is paused
        Toast.makeText(this, "Game Paused", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }
}
