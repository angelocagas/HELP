package com.angelodev.helpapp.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angelodev.helpapp.data.model.ProjectConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewProjectViewModel @Inject constructor() : ViewModel() {

    private val _navigateToInput = MutableLiveData<ProjectConfig?>()
    val navigateToInput: LiveData<ProjectConfig?> = _navigateToInput

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun onProceed(projectName: String, quantity: String, isTW: Boolean, isTHHN: Boolean) {
        if (projectName.isEmpty() || quantity.isEmpty()) {
            _errorMessage.value = "All fields are required"
            return
        }

        val quantityValue = quantity.toIntOrNull() ?: 0
        if (quantityValue > 30) {
            _errorMessage.value = "The maximum quantity is 30"
            return
        }
        if (!isTW && !isTHHN) {
            _errorMessage.value = "Please select a Wire for Ground option"
            return
        }
        if (quantityValue >= 25) {
            _errorMessage.value = "It may give some error in 25 Circuit"
            // Don't return - still proceed
        }

        val wireType = if (isTW) "TW" else "THHN"
        val panelBoard = if (isTW) "1PB" else "#PB"

        _navigateToInput.value = ProjectConfig(
            projectName = projectName,
            wireForGround = wireType,
            panelBoard = panelBoard
        )
    }

    fun onNavigationDone() {
        _navigateToInput.value = null
        _errorMessage.value = null
    }
}
