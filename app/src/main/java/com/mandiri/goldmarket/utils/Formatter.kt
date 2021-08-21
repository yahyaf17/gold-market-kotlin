package com.mandiri.goldmarket.utils

import android.icu.text.SimpleDateFormat
import java.math.BigDecimal
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object Formatter {

    private val locale = Locale("in", "ID")

    fun rupiahFormatter(value: Double): String {
        return NumberFormat.getCurrencyInstance(locale).format(value)
    }

    fun dateFormatter(value: String): String {
        var date = LocalDate.parse(value)
        var formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        return formatter.format(date)
    }

    fun gramFormat(value: Double): String {
        val gr = NumberFormat.getNumberInstance(locale).format(value)
        return "$gr gram"
    }
}
