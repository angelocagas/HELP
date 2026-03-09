package com.angelodev.helpapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.angelodev.helpapp.data.local.entity.CircuitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CircuitDao {

    @Insert
    suspend fun insert(circuit: CircuitEntity)

    @Update
    suspend fun update(circuit: CircuitEntity)

    @Query("SELECT * FROM circuits")
    fun getAllCircuits(): Flow<List<CircuitEntity>>

    @Query("SELECT * FROM circuits")
    suspend fun getAllCircuitsList(): List<CircuitEntity>

    @Query("SELECT DISTINCT item FROM circuits")
    suspend fun getDistinctItems(): List<String>

    @Query("SELECT `A` FROM circuits WHERE item LIKE 'ACU%' OR item = 'Refrigerator'")
    suspend fun getAmpereForACURefrigerator(): List<String>

    @Query("SELECT `A` FROM circuits WHERE item LIKE 'Lighting Outlet%'")
    suspend fun getAmpereForLightingOutlet(): List<String>

    @Query("DELETE FROM circuits")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM circuits")
    suspend fun getCircuitCount(): Int
}
