package com.assignment.openinapp.utils

import com.github.mikephil.charting.formatter.ValueFormatter

class IntegerValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return value.toInt().toString()
    }
}

class HourAxisValueFormatter(private val times: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return if (value.toInt() >= 0 && value.toInt() < times.size) {
            times[value.toInt()]
        } else {
            ""
        }
    }
}