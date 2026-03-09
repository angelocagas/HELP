package com.angelodev.helpapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelodev.helpapp.data.model.UserProfile
import com.angelodev.helpapp.data.repository.AuthRepository
import com.angelodev.helpapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _registerResult = MutableLiveData<Result<Unit>>()
    val registerResult: LiveData<Result<Unit>> = _registerResult

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    val isLoggedIn get() = authRepository.isLoggedIn

    fun register(name: String, phone: String, email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val authResult = authRepository.register(email, password)
            authResult.onSuccess { user ->
                val profile = UserProfile(fullname = name, contact = phone, email = email)
                userRepository.saveUser(user.uid, profile)
                _registerResult.value = Result.success(Unit)
            }.onFailure { e ->
                _registerResult.value = Result.failure(e)
            }
            _isLoading.value = false
        }
    }
}
