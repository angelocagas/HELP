package com.angelodev.helpapp.util

data class CircuitConfig(
    val defaultAT: String = "20",
    val defaultSizeMm: String = "3.5",
    val defaultGroundMm: String = "3.5",
    val defaultMmPlus: String = "20",
    val defaultQuantity: String = "",
    val quantityEditable: Boolean = true,
    val maxQuantity: Int = 100,
    val defaultWatts: String = "",
    val showHorsepower: Boolean = false,
    val showWattsDropdown: Boolean = false, // for Lighting Outlet wattage selection
    val sizeNum: String = "2",
    val groundNum: String = "1",
    val sizeType: String = "THHN",
    val groundType: String = "THW"
)

object CircuitDefaults {
    val ITEM_TYPES = arrayOf(
        "Lighting Outlet", "Convenience Outlet", "ACU",
        "Water Heater", "Range", "Refrigerator", "Spare"
    )

    val HORSEPOWER_VALUES = arrayOf(
        "1/6", "1/4", "1/3", "1/2", "3/4", "1",
        "1 1/2", "2", "3", "5", "7 1/2", "10"
    )

    val PIPE_TYPES = arrayOf("EMT", "PVC", "IMC")

    val LIGHTING_WATTAGES = arrayOf(
        "100", "95", "90", "85", "80", "75", "70", "65",
        "60", "55", "50", "45", "40", "35", "30", "25",
        "20", "15", "10", "5"
    )

    // Max quantity per wattage for Lighting Outlet
    val LIGHTING_MAX_QUANTITIES = mapOf(
        "100" to 27, "95" to 29, "90" to 30, "85" to 32,
        "80" to 34, "75" to 36, "70" to 39, "65" to 42,
        "60" to 46, "55" to 50, "50" to 55, "45" to 61,
        "40" to 69, "35" to 78, "30" to 92, "25" to 110,
        "20" to 138, "15" to 183, "10" to 275, "5" to 550
    )

    fun getConfig(itemType: String): CircuitConfig = when (itemType) {
        "Lighting Outlet" -> CircuitConfig(
            defaultAT = "15",
            defaultSizeMm = "3.5",
            defaultGroundMm = "3.5",
            showWattsDropdown = true
        )
        "Convenience Outlet" -> CircuitConfig(
            defaultAT = "20",
            defaultWatts = "180",
            maxQuantity = 20
        )
        "ACU" -> CircuitConfig(
            defaultAT = "20",
            quantityEditable = false,
            defaultQuantity = "1",
            showHorsepower = true
        )
        "Water Heater" -> CircuitConfig(
            defaultAT = "30",
            defaultSizeMm = "5.5",
            defaultGroundMm = "5.5",
            quantityEditable = false,
            defaultQuantity = "1"
        )
        "Range" -> CircuitConfig(
            defaultAT = "30",
            defaultSizeMm = "5.5",
            defaultGroundMm = "5.5",
            quantityEditable = false,
            defaultQuantity = "1"
        )
        "Refrigerator" -> CircuitConfig(
            defaultAT = "20",
            quantityEditable = false,
            defaultQuantity = "1"
        )
        "Spare" -> CircuitConfig(
            defaultAT = "20",
            defaultSizeMm = "Stub",
            defaultGroundMm = "",
            quantityEditable = false,
            defaultQuantity = "1",
            sizeNum = "",
            groundNum = "",
            sizeType = "UP",
            groundType = ""
        )
        else -> CircuitConfig()
    }

    data class HorsepowerSpec(
        val watts: String,
        val sizeMm: String,
        val groundMm: String,
        val ampere: String,
        val va: String,
        val at: String,
        val af: String,
        val mmPlus: String
    )

    val HORSEPOWER_SPECS = mapOf(
        "1/6" to HorsepowerSpec("506", "3.5", "3.5", "2.20", "506", "20", "50", "20"),
        "1/4" to HorsepowerSpec("667", "3.5", "3.5", "2.90", "667", "20", "50", "20"),
        "1/3" to HorsepowerSpec("828", "3.5", "3.5", "3.60", "828", "20", "50", "20"),
        "1/2" to HorsepowerSpec("1127", "3.5", "3.5", "4.90", "1127", "20", "50", "20"),
        "3/4" to HorsepowerSpec("1587", "3.5", "3.5", "6.90", "1587", "20", "50", "20"),
        "1" to HorsepowerSpec("1840", "3.5", "3.5", "8.00", "1840", "20", "50", "20"),
        "1 1/2" to HorsepowerSpec("2300", "3.5", "3.5", "10.00", "2300", "20", "50", "20"),
        "2" to HorsepowerSpec("2760", "5.5", "5.5", "12.00", "2760", "30", "50", "20"),
        "3" to HorsepowerSpec("3910", "5.5", "5.5", "17.00", "3910", "30", "50", "20"),
        "5" to HorsepowerSpec("6440", "8.0", "5.5", "28.00", "6440", "50", "50", "20"),
        "7 1/2" to HorsepowerSpec("9220", "22", "8.0", "40.00", "9220", "70", "100", "25"),
        "10" to HorsepowerSpec("11500", "30", "8.0", "50.00", "11500", "90", "100", "32")
    )

    /**
     * Returns appropriate Ampere Frame based on Ampere Trip per PEC.
     * Breaker frame must be >= AT rating.
     */
    fun getAmpereFrame(at: Int): String = when {
        at <= 50 -> "50"
        at <= 100 -> "100"
        at <= 225 -> "225"
        else -> "400"
    }

    // Default spare circuit (used when adding spare on odd circuit count)
    fun getDefaultSpareCircuit(projectName: String): Map<String, String> = mapOf(
        "projectName" to projectName,
        "quantity" to "1",
        "item" to "Spare",
        "oPlus" to "1",
        "voltage" to "230",
        "voltAmpere" to "1500",
        "ampere" to (1500.0 / 230.0).let {
            java.text.DecimalFormat("#.##").apply {
                roundingMode = java.math.RoundingMode.HALF_UP
            }.format(it)
        },
        "pole" to "2",
        "ampereTrip" to "20",
        "ampereFrame" to "50",
        "sizeNum" to "",
        "sizeMm" to "Stub",
        "sizeType" to "UP",
        "groundNum" to "",
        "groundMm" to "",
        "groundType" to "",
        "mmPlus" to "20",
        "conduitType" to "PVC"
    )
}
