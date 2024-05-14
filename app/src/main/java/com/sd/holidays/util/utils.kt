package com.sd.holidays.util

import java.time.LocalDate

fun String.getDay(): String = try {
    "$this (${
        LocalDate.parse(
            this.split("-").reversed().joinToString("-")
        ).dayOfWeek
    })"
} catch (e: Exception) {
    this
}

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