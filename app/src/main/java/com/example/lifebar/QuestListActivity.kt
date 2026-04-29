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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class QuestListActivity : AppCompatActivity() {

    private lateinit var rvQuestList: RecyclerView
    private lateinit var tvCategoryTitle: TextView
    private lateinit var pbExp: ProgressBar
    private lateinit var tvExp: TextView
    private lateinit var pbMp: ProgressBar
    private lateinit var tvMp: TextView
    private lateinit var tvPlayerLevel: TextView
    private lateinit var tvCoins: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_list)

        rvQuestList = findViewById(R.id.rv_quest_list)
        tvCategoryTitle = findViewById(R.id.tv_category_title)
        pbExp = findViewById(R.id.pb_exp)
        tvExp = findViewById(R.id.tv_exp)
        pbMp = findViewById(R.id.pb_mp)
        tvMp = findViewById(R.id.tv_mp)
        tvPlayerLevel = findViewById(R.id.tv_player_level)
        tvCoins = findViewById(R.id.tv_coins)

        findViewById<Button>(R.id.btn_back).setOnClickListener { finish() }

        findViewById<CardView>(R.id.cv_profile).setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        val kategori = intent.getStringExtra("KATEGORI_PILIHAN") ?: "MAIN"
        tvCategoryTitle.text = when(kategori) {
            "MAIN" -> "⚔️ MAIN QUEST"
            "SIDE" -> "🛡️ SIDE QUEST"
            else -> "⏳ DAILY QUEST"
        }

        updateHeader()

        val q = ArrayList<Quest>()
        q.add(Quest("Selesaikan UI Layout", "Desain XML aplikasi LifeBar.", "MAIN", 50, 100, -25))
        q.add(Quest("Implementasi Intent", "Kirim data antar Activity.", "MAIN", 40, 80, -20))
        q.add(Quest("Setup RecyclerView", "List misi dengan Adapter.", "MAIN", 45, 90, -20))
        q.add(Quest("Database Replication", "Tugas Distributed Database.", "MAIN", 60, 150, -30))
        q.add(Quest("Laporan OOAD", "Membuat Use Case Diagram.", "MAIN", 55, 120, -25))

        q.add(Quest("Rapikan Meja", "Bikin coding lebih fokus.", "SIDE", 20, 30, -5))
        q.add(Quest("Bantu Teman", "Fix error Gradle teman.", "SIDE", 25, 40, -10))
        q.add(Quest("Backup GitHub", "Simpan kodingan ke Cloud.", "SIDE", 15, 20, -5))
        q.add(Quest("Baca Manual Router", "Setup TP-Link WISP.", "SIDE", 10, 15, -5))
        q.add(Quest("Explore Library", "Coba library animasi.", "SIDE", 20, 35, -10))

        q.add(Quest("Minum Air 2L", "Fokus otak terjaga.", "DAILY", 10, 10, 15))
        q.add(Quest("Tidur Siang", "Power nap energi.", "DAILY", 5, 5, 30))
        q.add(Quest("Makan Nutrisi", "Stamina untuk coding.", "DAILY", 10, 15, 10))
        q.add(Quest("Olahraga Ringan", "Peregangan otot kaku.", "DAILY", 15, 20, 20))
        q.add(Quest("Ibadah/Tenang", "Fokus mental.", "DAILY", 5, 10, 25))

        val filtered = q.filter { it.category == kategori } as ArrayList<Quest>
        rvQuestList.layoutManager = LinearLayoutManager(this)
        rvQuestList.adapter = QuestAdapter(filtered)
    }

    fun updateHeader() {
        val sharedPref = getSharedPreferences("LifeBarData", Context.MODE_PRIVATE)
        val exp = sharedPref.getInt("EXP", 88)
        val mp = sharedPref.getInt("MP", 43)
        val lvl = sharedPref.getInt("LEVEL", 10)
        val c = sharedPref.getInt("COINS", 0)

        tvPlayerLevel.text = "RIO - Lv.$lvl Novice"
        tvCoins.text = "\uD83D\uDCB0 $c"
        ObjectAnimator.ofInt(pbExp, "progress", pbExp.progress, exp).setDuration(800).start()
        tvExp.text = "$exp / 100 EXP"
        ObjectAnimator.ofInt(pbMp, "progress", pbMp.progress, mp).setDuration(800).start()
        tvMp.text = "$mp / 100 MP"
    }
}