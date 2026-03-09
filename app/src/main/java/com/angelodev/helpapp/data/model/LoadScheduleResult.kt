package com.angelodev.helpapp.data.model

data class LoadScheduleResult(
    val totalVA: Double = 0.0,
    val totalAmpere: Double = 0.0,
    val highestAmpere: Double = 0.0,
    val demandFactor: Double = 1.0,
    val loadName: String = "",
    val mainPipeType: String = "PVC"
)
