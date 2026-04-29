package com.example.lifebar

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class QuestAdapter(private val questList: ArrayList<Quest>) : RecyclerView.Adapter<QuestAdapter.QuestViewHolder>() {

    class QuestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_quest_title)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_quest_desc)
        val tvReward: TextView = itemView.findViewById(R.id.tv_quest_reward)
        val btnComplete: Button = itemView.findViewById(R.id.btn_complete_quest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quest, parent, false)
        return QuestViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestViewHolder, position: Int) {
        val currentQuest = questList[position]

        holder.tvTitle.text = currentQuest.title
        holder.tvDesc.text = currentQuest.description

        val mpText = if (currentQuest.penaltyMp > 0) "+${currentQuest.penaltyMp} MP" else "${currentQuest.penaltyMp} MP"
        holder.tvReward.text = "[+${currentQuest.rewardExp} EXP]  [+${currentQuest.rewardCoins} \uD83E\uDE99]  [$mpText]"

        holder.btnComplete.setOnClickListener {
            val context = holder.itemView.context
            val sharedPref = context.getSharedPreferences("LifeBarData", Context.MODE_PRIVATE)

            var currentExp = sharedPref.getInt("EXP", 88)
            var currentMp = sharedPref.getInt("MP", 43)
            var currentLevel = sharedPref.getInt("LEVEL", 10)
            var currentCoins = sharedPref.getInt("COINS", 0)

            if (currentMp <= 0 && currentQuest.penaltyMp <= 0) {
                Toast.makeText(context, "Energi Habis! Lakukan misi DAILY.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            currentExp += currentQuest.rewardExp
            currentMp += currentQuest.penaltyMp
            currentCoins += currentQuest.rewardCoins

            if (currentMp < 0) currentMp = 0
            if (currentMp > 100) currentMp = 100

            var isLevelUp = false
            while (currentExp >= 100) {
                currentExp -= 100
                currentLevel += 1
                isLevelUp = true
            }

            sharedPref.edit()
                .putInt("EXP", currentExp)
                .putInt("MP", currentMp)
                .putInt("LEVEL", currentLevel)
                .putInt("COINS", currentCoins)
                .apply()

            if (isLevelUp) {
                Toast.makeText(context, "LEVEL UP! Level $currentLevel!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Misi Selesai! +${currentQuest.rewardCoins} Koin", Toast.LENGTH_SHORT).show()
            }

            if (context is QuestListActivity) {
                context.updateHeader()
            }

            holder.btnComplete.isEnabled = false
            holder.btnComplete.text = "SELESAI"
            holder.btnComplete.setBackgroundColor(Color.GRAY)
        }
    }

    override fun getItemCount(): Int = questList.size
}