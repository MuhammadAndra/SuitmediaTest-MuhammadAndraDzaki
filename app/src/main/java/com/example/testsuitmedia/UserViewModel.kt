package com.example.testsuitmedia

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testsuitmedia.api.ApiConfig
import com.example.testsuitmedia.model.User
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _users = mutableStateOf<List<User>>(emptyList())
    val users: State<List<User>> get() = _users

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    fun getUsers(page: Int, perPage: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.RetrofitInstance.apiService.getUsers(page, perPage)
                _users.value = _users.value + response.data // Menambahkan data baru ke data yang sudah ada
            } catch (e: Exception) {

            } finally {
                _isLoading.value = false
            }
        }
    }
    fun clearUsers() {
        _users.value = emptyList()
    }
}