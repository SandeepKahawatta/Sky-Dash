package com.example.skydash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val action = supportActionBar
        action?.hide()

        try {
            Handler().postDelayed({
                startActivity(Intent(this@WelcomeActivity, HomeActivity::class.java))
                finish() // Optional: Finish the welcome activity after starting the next activity
            }, 3000)

        }catch (e: Exception) {
            e.printStackTrace()
        }


    }
}