package com.peludosteam.ismarket.viewmode

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.peludosteam.ismarket.domain.User
import com.peludosteam.ismarket.repository.AuthRepository
import com.peludosteam.ismarket.repository.AuthRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class SignupViewModel(
    val repo: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    val authState = MutableLiveData(0)
    // 0. Idle
    // 1. Loading
    // 2. Error
    // 3. Success

    val errorMessage = MutableLiveData<String>()

    // Verificar si hay un usuario autenticado
    fun isUserLoggedIn(): Boolean {
        return repo.getCurrentUserUid() != null
    }

    // Cerrar sesión
    fun signout() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.signout() // Cerrar sesión utilizando el repositorio
                withContext(Dispatchers.Main) {
                    authState.value = 3 // Indicar que la sesión fue cerrada exitosamente
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    authState.value = 2 // Error al cerrar sesión
                    errorMessage.value = "Error al cerrar sesión: ${e.localizedMessage}"
                }
            }
        }
    }

    // Registrar usuario
    fun signup(user: User, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { authState.value = 1 } // Estado de carga
            try {
                repo.signup(user, password)
                withContext(Dispatchers.Main) {
                    authState.value = 3 // Registro exitoso
                }
            } catch (ex: FirebaseAuthWeakPasswordException) {
                withContext(Dispatchers.Main) {
                    authState.value = 2 // Estado de error
                    errorMessage.value = "La contraseña es demasiado débil. Usa al menos 6 caracteres."
                }
            } catch (ex: FirebaseAuthInvalidCredentialsException) {
                withContext(Dispatchers.Main) {
                    authState.value = 2 // Estado de error
                    errorMessage.value = "El correo electrónico tiene un formato inválido."
                }
            } catch (ex: FirebaseAuthInvalidUserException) {
                withContext(Dispatchers.Main) {
                    authState.value = 2 // Estado de error
                    errorMessage.value = "Este usuario ya existe. Intenta con otro correo."
                }
            } catch (ex: FirebaseAuthException) {
                withContext(Dispatchers.Main) {
                    authState.value = 2 // Estado de error
                    errorMessage.value = "Ocurrió un error durante el registro: ${ex.localizedMessage}"
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    authState.value = 2 // Estado de error
                    errorMessage.value = "Ha ocurrido un error inesperado. Por favor, intenta nuevamente."
                }
            }
        }
    }

    // Iniciar sesión
    fun signin(email: String, password: String, param: (Any) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    authState.value = 1 // Estado de carga
                }
                repo.signin(email, password)
                withContext(Dispatchers.Main) {
                    authState.value = 3 // Inicio de sesión exitoso
                }
            } catch (ex: FirebaseAuthInvalidUserException) {
                withContext(Dispatchers.Main) {
                    authState.value = 2 // Estado de error
                    errorMessage.value = "El usuario no existe. Verifica tu correo electrónico."
                }
            } catch (ex: FirebaseAuthInvalidCredentialsException) {
                withContext(Dispatchers.Main) {
                    authState.value = 2 // Estado de error
                    errorMessage.value = "La contraseña es incorrecta. Intenta nuevamente."
                }
            } catch (ex: FirebaseAuthException) {
                withContext(Dispatchers.Main) {
                    authState.value = 2 // Estado de error
                    errorMessage.value = "Ocurrió un error al iniciar sesión: ${ex.localizedMessage}"
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    authState.value = 2 // Estado de error
                    errorMessage.value = "Ha ocurrido un error inesperado al iniciar sesión."
                }
            }
        }
    }
}
