package com.example.mvvmlogin.ui.login.presentation

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class LoginViewModel: ViewModel() {


    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _loginEnabled = MutableLiveData<Boolean>()
    private val _isLoading = MutableLiveData<Boolean>()

    val email: LiveData<String> = _email
    val password: LiveData<String> = _password
    val loginEnabled: LiveData<Boolean> = _loginEnabled
    val isLoading: LiveData<Boolean> = _isLoading


    fun onLoginChange(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnabled.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length >= 6

    suspend fun onLoginSelected(){
        _isLoading.value = true
        delay (2000)
        _isLoading.value = false
    }

}

