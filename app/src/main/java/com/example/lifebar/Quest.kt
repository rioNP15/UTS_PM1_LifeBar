package com.example.lifebar

data class Quest(
    val title: String,
    val description: String,
    val category: String,
    val rewardExp: Int,
    val rewardCoins: Int,
    val penaltyMp: Int
)