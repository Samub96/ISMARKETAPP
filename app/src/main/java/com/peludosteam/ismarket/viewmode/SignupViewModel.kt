package com.peludosteam.ismarket.viewmode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.peludosteam.ismarket.domain.User
import com.peludosteam.ismarket.repository.AuthRepository
import com.peludosteam.ismarket.repository.AuthRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignupViewModel(
    val repo: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {
    val authState = MutableLiveData(0)
    //0. Idle
    //1. Loading
    //2. Error
    //3. Success

    fun signup(user: User, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { authState.value = 1 }
            try {
                repo.signup(user, password)
                withContext(Dispatchers.Main) { authState.value = 3 }
            } catch (ex: FirebaseAuthException) {
                withContext(Dispatchers.Main) { authState.value = 2 }
                ex.printStackTrace()
            }
        }
    }

    fun signin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) { authState.value = 1 }
                repo.signin(email, password)
                withContext(Dispatchers.Main) { authState.value = 3 }
            }catch (ex:FirebaseAuthException){
                withContext(Dispatchers.Main) { authState.value = 2 }
                ex.printStackTrace()
            }
        }
    }

}