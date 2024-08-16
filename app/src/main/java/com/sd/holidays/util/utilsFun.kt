package com.sd.holidays.util

import android.content.Context
import android.widget.Toast

fun checkInputText(text: String): Boolean {
    if (text.isEmpty() || text.length != 4) return false
    return try {
        val number: Int = text.toInt()
        number in 1974..2074
    } catch (e: Exception) {
        false
    }
}

fun getEnd(number: Int): String {
    val newNumber = number % 100
    return when {
        (newNumber % 10 == 1 && newNumber != 11) -> "в $number стране"
        newNumber == 0 -> "нигде"
        else -> "в $number странах"
    }
}

fun toast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}