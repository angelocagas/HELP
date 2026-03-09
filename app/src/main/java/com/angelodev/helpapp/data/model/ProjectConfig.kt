package com.angelodev.helpapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProjectConfig(
    val projectName: String,
    val wireForGround: String, // "TW" or "THHN"
    val panelBoard: String     // "1PB" or "#PB"
) : Parcelable
