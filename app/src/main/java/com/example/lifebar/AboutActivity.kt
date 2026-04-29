package com.example.lifebar

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        findViewById<Button>(R.id.btn_back_about).setOnClickListener { finish() }

        val sharedPref = getSharedPreferences("LifeBarData", Context.MODE_PRIVATE)
        val currentLevel = sharedPref.getInt("LEVEL", 10)
        val currentExp = sharedPref.getInt("EXP", 88)
        val currentCoins = sharedPref.getInt("COINS", 0)

        findViewById<TextView>(R.id.tv_profile_level).text = "Level: $currentLevel Novice"
        findViewById<TextView>(R.id.tv_profile_exp).text = "EXP Saat Ini: $currentExp / 100"
        findViewById<TextView>(R.id.tv_profile_coins).text = "Total Koin: $currentCoins \uD83E\uDE99"
    }
}