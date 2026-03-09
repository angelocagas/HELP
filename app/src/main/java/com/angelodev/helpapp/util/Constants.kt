package com.angelodev.helpapp.util

object Constants {
    const val VOLTAGE = 230.0
    const val MAX_CIRCUITS = 30
    const val MIN_PREVIEW_CIRCUITS = 2

    // SharedPreferences
    const val PREFS_NAME = "HelpAppPrefs"
    const val KEY_MAIN_BREAKER = "mainBreaker"
    const val KEY_FEEDER_WIRE_TYPE = "feederWireType"
    const val KEY_PIPE_SIZE = "pipeSize"
    const val KEY_DEMAND_FACTOR = "demandFactor"
    const val KEY_MAIN_PIPE = "mainPipe"
    const val KEY_LOAD_NAME = "loadName"
    const val KEY_UPDATED_MAIN = "UMT"
    const val KEY_PIPE_WIRE = "TT"

    // Intent extras
    const val EXTRA_PROJECT_CONFIG = "projectConfig"
    const val EXTRA_CIRCUIT = "circuit"
    const val EXTRA_EDIT_MODE = "editMode"
    const val EXTRA_TOTAL_VA = "TOTALVA"
    const val EXTRA_TOTAL_A = "TOTALA"
    const val EXTRA_HIGHEST_A = "HIGHA"
    const val EXTRA_DEMAND = "DEMAND"
    const val EXTRA_MAIN_PIPE = "mainpipo"
    const val EXTRA_LOAD_NAME = "loadnamesave"
    const val EXTRA_EXECUTE_CODE_2 = "executeCode2"
    const val EXTRA_EXECUTE_CODE_3 = "executeCode3"
    const val EXTRA_NUM_PVC = "NUMPVC"
}
