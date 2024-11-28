package com.peludosteam.ismarket.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.domain.User
import com.peludosteam.ismarket.service.AuthService
import com.peludosteam.ismarket.service.AuthServiceImpl
import kotlinx.coroutines.tasks.await

interface AuthRepository {
    suspend fun signup(user: User, password: String)
    suspend fun signin(email: String, password: String)
    suspend fun updatePassword(newPassword: String): Boolean // Nueva función para actualizar la contraseña
}

class AuthRepositoryImpl(
    val authService: AuthService = AuthServiceImpl(),
    val userRepository: UserRepository = UserRepositoryImpl()
) : AuthRepository {
    override suspend fun signup(user: User, password: String) {
        // 1. Registro en módulo de autenticación
        authService.createUser(user.email, password)

        // 2. Obtenemos el UID
        val uid = Firebase.auth.currentUser?.uid

        // 3. Crear el usuario en Firestore
        uid?.let {
            user.id = it
            userRepository.createUser(user)
        }
    }

    override suspend fun signin(email: String, password: String) {
        authService.loginWithEmailAndPassword(email, password)
    }

    // Implementación de la actualización de contraseña
    override suspend fun updatePassword(newPassword: String): Boolean {
        return try {
            val user = Firebase.auth.currentUser
            if (user != null) {
                user.updatePassword(newPassword).await() // Usamos 'await' para esperar la actualización
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
