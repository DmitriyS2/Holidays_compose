package com.sd.holidays.util.extensions

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