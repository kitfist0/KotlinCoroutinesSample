package com.coroutines.sample

// Review
// https://api.coinpaprika.com/#tag/Coins
data class Coin(
    var id: String,
    var name: String,
    var symbol: String,
    val rank: Int,
    val is_new: Boolean,
    val is_active: Boolean,
    val type: String
)