package com.angelodev.helpapp.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelodev.helpapp.data.local.entity.CircuitEntity
import com.angelodev.helpapp.data.repository.CircuitRepository
import com.angelodev.helpapp.util.PrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadScheduleViewModel @Inject constructor(
    private val repository: CircuitRepository,
    val prefsManager: PrefsManager
) : ViewModel() {

    private val _circuits = MutableLiveData<List<CircuitEntity>>()
    val circuits: LiveData<List<CircuitEntity>> = _circuits

    private val _allItems = MutableLiveData<List<String>>()
    val allItems: LiveData<List<String>> = _allItems

    private val _allATs = MutableLiveData<List<String>>()
    val allATs: LiveData<List<String>> = _allATs

    private val _allAs = MutableLiveData<List<String>>()
    val allAs: LiveData<List<String>> = _allAs

    private val _allVAs = MutableLiveData<List<String>>()
    val allVAs: LiveData<List<String>> = _allVAs

    private val _highestACUA = MutableLiveData<Double>()
    val highestACUA: LiveData<Double> = _highestACUA

    fun loadData() {
        viewModelScope.launch {
            val circuitList = repository.getAllCircuitsList()
            _circuits.value = circuitList

            // All items (names)
            _allItems.value = circuitList.map { it.item }

            // All AT values
            _allATs.value = circuitList.map { it.ampereTrip }

            // All A values
            _allAs.value = circuitList.map { it.ampere }

            // All VA values
            _allVAs.value = circuitList.map { it.voltAmpere }

            // Highest A for ACU/Refrigerator
            val acuRefList = repository.getAmpereForACURefrigerator()
            var highest = 0.0
            for (aStr in acuRefList) {
                try {
                    val a = aStr.toDouble()
                    if (a > highest) highest = a
                } catch (_: NumberFormatException) {
                }
            }
            _highestACUA.value = highest
        }
    }

    fun clearDatabase() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun refreshData() {
        loadData()
    }
}
