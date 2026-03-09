package com.angelodev.helpapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelodev.helpapp.data.repository.CircuitRepository
import com.angelodev.helpapp.util.PrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CircuitRepository,
    private val prefsManager: PrefsManager
) : ViewModel() {

    fun clearProjectData() {
        viewModelScope.launch {
            repository.deleteAll()
            prefsManager.clear()
        }
    }
}
