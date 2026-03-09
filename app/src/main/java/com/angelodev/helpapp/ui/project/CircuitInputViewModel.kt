package com.angelodev.helpapp.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelodev.helpapp.data.local.entity.CircuitEntity
import com.angelodev.helpapp.data.model.LoadScheduleResult
import com.angelodev.helpapp.data.model.ProjectConfig
import com.angelodev.helpapp.data.repository.CircuitRepository
import com.angelodev.helpapp.util.CircuitDefaults
import com.angelodev.helpapp.util.ElectricalCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CircuitInputViewModel @Inject constructor(
    private val repository: CircuitRepository
) : ViewModel() {

    // Running totals
    private var totalVAValue = 0.0
    private var totalAValue = 0.0
    private val ampereList = mutableListOf<Double>()

    private val _circuitCount = MutableLiveData(0)
    val circuitCount: LiveData<Int> = _circuitCount

    private val _totalVA = MutableLiveData(0.0)
    val totalVA: LiveData<Double> = _totalVA

    private val _totalA = MutableLiveData(0.0)
    val totalA: LiveData<Double> = _totalA

    private val _navigateToSchedule = MutableLiveData<LoadScheduleResult?>()
    val navigateToSchedule: LiveData<LoadScheduleResult?> = _navigateToSchedule

    private val _circuitSaved = MutableLiveData<Boolean>()
    val circuitSaved: LiveData<Boolean> = _circuitSaved

    fun loadCircuitCount() {
        viewModelScope.launch {
            val count = repository.getCircuitCount()
            _circuitCount.value = count
        }
    }

    fun computeAndSaveCircuit(
        projectName: String,
        quantity: String,
        item: String,
        watts: String,
        oPlus: String = "1",
        voltage: String = "230",
        pole: String = "2",
        ampereTrip: String,
        ampereFrame: String = "50",
        sizeNum: String,
        sizeMm: String,
        sizeType: String,
        groundNum: String,
        groundMm: String,
        groundType: String,
        mmPlus: String,
        conduitType: String,
        precomputedVA: String? = null,
        precomputedA: String? = null
    ) {
        val quantityInt = quantity.toIntOrNull() ?: 1
        val wattsDouble = watts.toDoubleOrNull() ?: 0.0

        val va = precomputedVA ?: ElectricalCalculator.formatValue(
            ElectricalCalculator.computeVA(quantityInt, wattsDouble)
        )
        val vaDouble = va.toDoubleOrNull() ?: 0.0
        val ampere = precomputedA ?: ElectricalCalculator.formatValue(
            ElectricalCalculator.computeAmpere(vaDouble)
        )
        val ampereDouble = ampere.toDoubleOrNull() ?: 0.0

        totalVAValue += vaDouble
        totalAValue += ampereDouble
        _totalVA.value = totalVAValue
        _totalA.value = totalAValue

        // Track ampere for ACU, Refrigerator, and Lighting Outlet (for highest amp calc)
        val baseItem = item.split("\n").firstOrNull()?.trim() ?: item
        if (baseItem.startsWith("ACU") || baseItem.startsWith("Refrigerator") || baseItem.startsWith("Lighting Outlet")) {
            ampereList.add(ampereDouble)
        }

        val circuit = CircuitEntity(
            projectName = projectName,
            quantity = quantity,
            item = item,
            oPlus = oPlus,
            voltage = voltage,
            voltAmpere = va,
            ampere = ampere,
            pole = pole,
            ampereTrip = ampereTrip,
            ampereFrame = ampereFrame,
            sizeNum = sizeNum,
            sizeMm = sizeMm,
            sizeType = sizeType,
            groundNum = groundNum,
            groundMm = groundMm,
            groundType = groundType,
            mmPlus = mmPlus,
            conduitType = conduitType
        )

        viewModelScope.launch {
            repository.insert(circuit)
            _circuitCount.value = repository.getCircuitCount()
            _circuitSaved.value = true
        }
    }

    fun updateCircuit(
        existingCircuit: CircuitEntity,
        projectName: String,
        quantity: String,
        item: String,
        watts: String,
        oPlus: String = "1",
        voltage: String = "230",
        pole: String = "2",
        ampereTrip: String,
        ampereFrame: String = "50",
        sizeNum: String,
        sizeMm: String,
        sizeType: String,
        groundNum: String,
        groundMm: String,
        groundType: String,
        mmPlus: String,
        conduitType: String,
        precomputedVA: String? = null,
        precomputedA: String? = null
    ) {
        val quantityInt = quantity.toIntOrNull() ?: 1
        val wattsDouble = watts.toDoubleOrNull() ?: 0.0

        val va = precomputedVA ?: ElectricalCalculator.formatValue(
            ElectricalCalculator.computeVA(quantityInt, wattsDouble)
        )
        val vaDouble = va.toDoubleOrNull() ?: 0.0
        val ampere = precomputedA ?: ElectricalCalculator.formatValue(
            ElectricalCalculator.computeAmpere(vaDouble)
        )

        val updated = existingCircuit.copy(
            projectName = projectName,
            quantity = quantity,
            item = item,
            oPlus = oPlus,
            voltage = voltage,
            voltAmpere = va,
            ampere = ampere,
            pole = pole,
            ampereTrip = ampereTrip,
            ampereFrame = ampereFrame,
            sizeNum = sizeNum,
            sizeMm = sizeMm,
            sizeType = sizeType,
            groundNum = groundNum,
            groundMm = groundMm,
            groundType = groundType,
            mmPlus = mmPlus,
            conduitType = conduitType
        )

        viewModelScope.launch {
            repository.update(updated)
            _circuitSaved.value = true
        }
    }

    fun proceedToSchedule(demandFactor: Double = 1.0, loadName: String = "", mainPipe: String = "") {
        val highestAmp = ElectricalCalculator.findHighestAmpere(ampereList)
        _navigateToSchedule.value = LoadScheduleResult(
            totalVA = totalVAValue,
            totalAmpere = totalAValue,
            highestAmpere = highestAmp,
            demandFactor = demandFactor,
            loadName = loadName,
            mainPipeType = mainPipe
        )
    }

    fun onNavigationDone() {
        _navigateToSchedule.value = null
        _circuitSaved.value = false
    }

    fun addSpareAndProceed(
        projectName: String,
        demandFactor: Double,
        loadName: String,
        mainPipe: String
    ) {
        val defaults = CircuitDefaults.getDefaultSpareCircuit(projectName)
        val va = defaults["voltAmpere"] ?: "1500"
        val ampere = defaults["ampere"] ?: "6.52"

        totalVAValue += (va.toDoubleOrNull() ?: 0.0)
        totalAValue += (ampere.toDoubleOrNull() ?: 0.0)

        val circuit = CircuitEntity(
            projectName = projectName,
            quantity = defaults["quantity"] ?: "1",
            item = defaults["item"] ?: "Spare",
            oPlus = defaults["oPlus"] ?: "1",
            voltage = defaults["voltage"] ?: "230",
            voltAmpere = va,
            ampere = ampere,
            pole = defaults["pole"] ?: "2",
            ampereTrip = defaults["ampereTrip"] ?: "20",
            ampereFrame = defaults["ampereFrame"] ?: "50",
            sizeNum = defaults["sizeNum"] ?: "",
            sizeMm = defaults["sizeMm"] ?: "Stub",
            sizeType = defaults["sizeType"] ?: "UP",
            groundNum = defaults["groundNum"] ?: "",
            groundMm = defaults["groundMm"] ?: "",
            groundType = defaults["groundType"] ?: "",
            mmPlus = defaults["mmPlus"] ?: "20",
            conduitType = defaults["conduitType"] ?: "PVC"
        )

        viewModelScope.launch {
            repository.insert(circuit)
            _circuitCount.value = repository.getCircuitCount()
            proceedToSchedule(demandFactor, loadName, mainPipe)
        }
    }
}
