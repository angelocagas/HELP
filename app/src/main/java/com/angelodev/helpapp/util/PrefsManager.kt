package com.angelodev.helpapp.util

import android.content.Context
import android.content.SharedPreferences

class PrefsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        Constants.PREFS_NAME, Context.MODE_PRIVATE
    )

    var mainBreaker: String
        get() = prefs.getString(Constants.KEY_MAIN_BREAKER, "") ?: ""
        set(value) = prefs.edit().putString(Constants.KEY_MAIN_BREAKER, value).apply()

    var feederWireType: String
        get() = prefs.getString(Constants.KEY_FEEDER_WIRE_TYPE, "TW") ?: "TW"
        set(value) = prefs.edit().putString(Constants.KEY_FEEDER_WIRE_TYPE, value).apply()

    var pipeSize: String
        get() = prefs.getString(Constants.KEY_PIPE_SIZE, "") ?: ""
        set(value) = prefs.edit().putString(Constants.KEY_PIPE_SIZE, value).apply()

    var demandFactor: String
        get() = prefs.getString(Constants.KEY_DEMAND_FACTOR, "1.00") ?: "1.00"
        set(value) = prefs.edit().putString(Constants.KEY_DEMAND_FACTOR, value).apply()

    var mainPipe: String
        get() = prefs.getString(Constants.KEY_MAIN_PIPE, "") ?: ""
        set(value) = prefs.edit().putString(Constants.KEY_MAIN_PIPE, value).apply()

    var loadName: String
        get() = prefs.getString(Constants.KEY_LOAD_NAME, "") ?: ""
        set(value) = prefs.edit().putString(Constants.KEY_LOAD_NAME, value).apply()

    var updatedMain: String
        get() = prefs.getString(Constants.KEY_UPDATED_MAIN, "") ?: ""
        set(value) = prefs.edit().putString(Constants.KEY_UPDATED_MAIN, value).apply()

    var pipeWire: String
        get() = prefs.getString(Constants.KEY_PIPE_WIRE, "") ?: ""
        set(value) = prefs.edit().putString(Constants.KEY_PIPE_WIRE, value).apply()

    fun clear() = prefs.edit().clear().apply()
}
