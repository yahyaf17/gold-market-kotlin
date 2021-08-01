package com.mandiri.goldmarket.utils

import android.icu.text.SimpleDateFormat
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

object Formatter {

    private val locale = Locale("in", "ID")

    fun rupiahFormatter(value: BigDecimal): String {
        return NumberFormat.getCurrencyInstance(locale).format(value)
    }

    fun dateFormatter(value: Date): String {
        val formatter = SimpleDateFormat("dd MMMM yyyy", locale)
        return formatter.format(value)
    }
}
