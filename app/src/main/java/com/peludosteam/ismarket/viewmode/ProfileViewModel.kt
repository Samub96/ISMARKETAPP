package com.peludosteam.ismarket.viewmode


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peludosteam.ismarket.Domain.User
import com.peludosteam.ismarket.repository.UserRepository
import com.peludosteam.ismarket.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    val userRepository: UserRepository = UserRepositoryImpl()
) : ViewModel() {

    private val _user = MutableLiveData<User?>(User())
    val user: MutableLiveData<User?> get() = _user

    fun getCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val me = userRepository.getCurrentUser()
            withContext(Dispatchers.Main) {
                _user.value = me
            }
        }
    }
}
