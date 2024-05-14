package com.android.ml_challenge.ui.util

fun Double.formatMoney(): String {
    return "R$ " + String.format("%.2f", this)
}