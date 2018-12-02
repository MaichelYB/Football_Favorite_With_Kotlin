package com.example.maichel.submission2.util

import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun TextView.bold() {
    typeface = Typeface.DEFAULT_BOLD
}

fun String.formatDate(fromDateFormat: String = "dd/MM/yy", toDateFormat: String = "E, dd MMM yyyy"): String {
    val date = SimpleDateFormat(fromDateFormat).parse(this)
    val dateFormatter = SimpleDateFormat(toDateFormat)
    return dateFormatter.format(date)
}

fun String.sentences(delimiter: String = ";", replacement: String = System.getProperty("line.separator")): String {
    return this.replace(delimiter, replacement)
}

fun toSimpleString(date: Date?): String? = with(date ?: Date()) {
    SimpleDateFormat("EEE, dd MMM yyy").format(this)
}