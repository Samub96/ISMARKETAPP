package com.peludosteam.ismarket.service

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.domain.User
import kotlinx.coroutines.tasks.await

interface UserServices {
    suspend fun createUser(user: User)
    suspend fun getUserById(id: String): User?
    suspend fun updateUser(user: User) // Nuevo método para actualizar usuario
    suspend fun getAllUsers():List<User?>


}
class UserServicesImpl: UserServices {
    override suspend fun createUser(user: User) {
        Firebase.firestore
            .collection("User")
            .document(user.id)
            .set(user)
            .await()
    }

    override suspend fun getUserById(id: String): User? {
        val user = Firebase.firestore
            .collection("User")
            .document(id)
            .get()
            .await()
        val userObject = user.toObject(User::class.java)
        return userObject
    }

    override suspend fun getAllUsers(): List<User?> {
        val userList = Firebase.firestore
            .collection("User")
            .get()
            .await()
        return userList.documents.map { document ->
            document.toObject(User::class.java)
        }
    }
    // Implementación del nuevo método updateUser
    override suspend fun updateUser(user: User) {
        Firebase.firestore
            .collection("User")
            .document(user.id)
            .update(
                "name", user.name,
                "username", user.username,
                "email", user.email
                // Si también deseas actualizar la contraseña, se puede agregar como:
                // "password", user.password
            ).await()
    }
}
