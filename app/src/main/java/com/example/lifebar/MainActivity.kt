package com.example.lifebar

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    private lateinit var pbExp: ProgressBar
    private lateinit var tvExp: TextView
    private lateinit var pbMp: ProgressBar
    private lateinit var tvMp: TextView
    private lateinit var tvPlayerLevel: TextView
    private lateinit var tvCoins: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pbExp = findViewById(R.id.pb_exp)
        tvExp = findViewById(R.id.tv_exp)
        pbMp = findViewById(R.id.pb_mp)
        tvMp = findViewById(R.id.tv_mp)
        tvPlayerLevel = findViewById(R.id.tv_player_level)
        tvCoins = findViewById(R.id.tv_coins)

        findViewById<Button>(R.id.btn_main).setOnClickListener { startQuestIntent("MAIN") }
        findViewById<Button>(R.id.btn_side).setOnClickListener { startQuestIntent("SIDE") }
        findViewById<Button>(R.id.btn_daily).setOnClickListener { startQuestIntent("DAILY") }
        findViewById<Button>(R.id.btn_shop).setOnClickListener { startActivity(Intent(this, ShopActivity::class.java)) }

        findViewById<CardView>(R.id.cv_profile).setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }

    private fun startQuestIntent(kategori: String) {
        val intent = Intent(this, QuestListActivity::class.java)
        intent.putExtra("KATEGORI_PILIHAN", kategori)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = getSharedPreferences("LifeBarData", Context.MODE_PRIVATE)
        val currentExp = sharedPref.getInt("EXP", 88)
        val currentMp = sharedPref.getInt("MP", 43)
        val currentLevel = sharedPref.getInt("LEVEL", 10)
        val currentCoins = sharedPref.getInt("COINS", 0)

        tvPlayerLevel.text = "RIO - Lv.$currentLevel Novice"
        tvCoins.text = "\uD83D\uDCB0 $currentCoins"

        ObjectAnimator.ofInt(pbExp, "progress", pbExp.progress, currentExp).setDuration(1000).start()
        tvExp.text = "$currentExp / 100 EXP"

        ObjectAnimator.ofInt(pbMp, "progress", pbMp.progress, currentMp).setDuration(1000).start()
        tvMp.text = "$currentMp / 100 MP"
    }
}