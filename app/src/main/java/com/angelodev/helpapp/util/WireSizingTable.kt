package com.angelodev.helpapp.util

object WireSizingTable {
    // Standard AT wire values
    val AT_VALUES = listOf(15, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 125, 150, 175)

    // Wire sizes based on ampere trip per PEC Table 3.10.1.62 (THHN copper ampacity)
    fun getWireSizeForAT(at: Int): String = when {
        at <= 20 -> "3.5"    // 30A ampacity
        at <= 30 -> "5.5"    // 40A ampacity
        at <= 50 -> "8.0"    // 55A ampacity
        at <= 70 -> "14"     // 75A ampacity
        at <= 90 -> "22"     // 100A ampacity
        at <= 100 -> "30"    // 115A ampacity
        at <= 125 -> "38"    // 130A ampacity
        at <= 150 -> "50"    // 160A ampacity
        at <= 175 -> "60"    // 190A ampacity
        at <= 200 -> "80"    // 220A ampacity
        else -> "100"        // 260A ampacity
    }

    // Ground wire size based on ampere trip per PEC Table 2.50.1.86
    fun getGroundWireSizeForAT(at: Int): String = when {
        at <= 15 -> "3.5"
        at <= 20 -> "3.5"
        at <= 30 -> "5.5"
        at <= 60 -> "5.5"
        at <= 100 -> "8.0"
        at <= 200 -> "14"
        else -> "22"
    }
}
