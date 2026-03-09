package com.angelodev.helpapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelodev.helpapp.data.model.UserProfile
import com.angelodev.helpapp.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _userProfile = MutableLiveData<UserProfile?>()
    val userProfile: LiveData<UserProfile?> = _userProfile

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> = _userId

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val user = auth.currentUser ?: return
        _userId.value = user.uid

        viewModelScope.launch {
            userRepository.observeUser(user.uid).collectLatest { profile ->
                _userProfile.value = profile
            }
        }
    }

    fun signOut() {
        auth.signOut()
    }
}
