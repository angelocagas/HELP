package com.angelodev.helpapp.data.repository

import com.angelodev.helpapp.data.local.dao.CircuitDao
import com.angelodev.helpapp.data.local.entity.CircuitEntity
import kotlinx.coroutines.flow.Flow

class CircuitRepository(private val dao: CircuitDao) {

    val allCircuits: Flow<List<CircuitEntity>> = dao.getAllCircuits()

    suspend fun insert(circuit: CircuitEntity) = dao.insert(circuit)

    suspend fun update(circuit: CircuitEntity) = dao.update(circuit)

    suspend fun getAllCircuitsList() = dao.getAllCircuitsList()

    suspend fun getDistinctItems() = dao.getDistinctItems()

    suspend fun getAmpereForACURefrigerator() = dao.getAmpereForACURefrigerator()

    suspend fun getAmpereForLightingOutlet() = dao.getAmpereForLightingOutlet()

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun getCircuitCount() = dao.getCircuitCount()
}
