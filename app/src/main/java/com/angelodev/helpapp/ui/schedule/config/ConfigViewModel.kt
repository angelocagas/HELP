package com.angelodev.helpapp.ui.schedule.config

import androidx.lifecycle.ViewModel
import com.angelodev.helpapp.util.PrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    val prefsManager: PrefsManager
) : ViewModel()
