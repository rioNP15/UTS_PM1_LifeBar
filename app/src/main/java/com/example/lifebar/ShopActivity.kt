package com.example.lifebar

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ShopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val tvShopCoins = findViewById<TextView>(R.id.tv_shop_coins)
        val sharedPref = getSharedPreferences("LifeBarData", Context.MODE_PRIVATE)

        var currentCoins = sharedPref.getInt("COINS", 0)
        tvShopCoins.text = "\uD83D\uDCB0 $currentCoins"

        findViewById<Button>(R.id.btn_back_shop).setOnClickListener { finish() }

        findViewById<Button>(R.id.btn_buy_1).setOnClickListener {
            if (currentCoins >= 200) {
                currentCoins -= 200
                sharedPref.edit().putInt("COINS", currentCoins).apply()
                tvShopCoins.text = "\uD83D\uDCB0 $currentCoins"
                Toast.makeText(this, "Berhasil membeli Es Kopi Susu!", Toast.LENGTH_LONG).show()
            } else {
                val kurang = 200 - currentCoins
                Toast.makeText(this, "Koin tidak cukup! Kurang $kurang Koin.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}