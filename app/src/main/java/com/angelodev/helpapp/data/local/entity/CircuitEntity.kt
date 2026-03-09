package com.angelodev.helpapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "circuits")
data class CircuitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val projectName: String = "",

    val quantity: String = "",

    val item: String = "",

    @ColumnInfo(name = "OPlus")
    val oPlus: String = "",

    @ColumnInfo(name = "V")
    val voltage: String = "",

    @ColumnInfo(name = "VA")
    val voltAmpere: String = "",

    @ColumnInfo(name = "A")
    val ampere: String = "",

    @ColumnInfo(name = "P")
    val pole: String = "",

    @ColumnInfo(name = "AT")
    val ampereTrip: String = "",

    @ColumnInfo(name = "AF")
    val ampereFrame: String = "",

    @ColumnInfo(name = "SNUM")
    val sizeNum: String = "",

    @ColumnInfo(name = "SMM")
    val sizeMm: String = "",

    @ColumnInfo(name = "STYPE")
    val sizeType: String = "",

    @ColumnInfo(name = "GNUM")
    val groundNum: String = "",

    @ColumnInfo(name = "GMM")
    val groundMm: String = "",

    @ColumnInfo(name = "GTYPE")
    val groundType: String = "",

    @ColumnInfo(name = "MMPlus")
    val mmPlus: String = "",

    @ColumnInfo(name = "CTYPE")
    val conduitType: String = ""
) : Parcelable
