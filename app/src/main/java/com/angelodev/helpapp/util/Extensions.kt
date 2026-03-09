package com.angelodev.helpapp.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.formatDecimal(pattern: String = "#.##"): String {
    val df = DecimalFormat(pattern)
    df.roundingMode = RoundingMode.HALF_UP
    return df.format(this)
}

fun Double.formatTwo(): String = DecimalFormat("#0.00").apply {
    roundingMode = RoundingMode.HALF_UP
}.format(this)

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.visible() { visibility = View.VISIBLE }
fun View.gone() { visibility = View.GONE }
fun View.invisible() { visibility = View.INVISIBLE }

fun String.toDoubleOrDefault(default: Double = 0.0): Double =
    toDoubleOrNull() ?: default

fun String.toIntOrDefault(default: Int = 0): Int =
    toIntOrNull() ?: default
