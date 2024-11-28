package com.peludosteam.ismarket.viewmode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.peludosteam.ismarket.domain.User
import com.peludosteam.ismarket.repository.AuthRepository
import com.peludosteam.ismarket.repository.AuthRepositoryImpl
import com.peludosteam.ismarket.repository.UserRepository
import com.peludosteam.ismarket.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    val userRepository: UserRepository = UserRepositoryImpl(),
    val authRepository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    val authState = MutableLiveData(0)  // 0: Idle, 1: Loading, 2: Error, 3: Success
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

    // Función para actualizar la contraseña
    fun updatePassword(newPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) { authState.value = 1 }  // Cargando
                val success = authRepository.updatePassword(newPassword)
                withContext(Dispatchers.Main) {
                    if (success) {
                        authState.value = 3  // Éxito
                    } else {
                        authState.value = 2  // Error
                    }
                }
            } catch (ex: FirebaseAuthException) {
                withContext(Dispatchers.Main) { authState.value = 2 } // Error
                ex.printStackTrace()
            }
        }
    }

    // Función para actualizar el perfil sin la contraseña
    fun updateUserProfile(name: String, username: String, email: String, password: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedUser = User(
                name = name,
                username = username,
                email = email,
                //password = password // La contraseña se pasa como opcional
            )

            // Si la contraseña no es null ni vacía, la actualizamos
            if (!password.isNullOrEmpty()) {
                updatePassword(password)  // Actualizar contraseña en Firebase
            }

            // Actualizamos los datos del usuario
            userRepository.updateUser(updatedUser)

            withContext(Dispatchers.Main) {
                _user.value = updatedUser
            }
        }
    }
}

