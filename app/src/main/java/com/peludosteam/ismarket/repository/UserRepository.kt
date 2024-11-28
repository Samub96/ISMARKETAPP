package com.peludosteam.ismarket.repository


import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.domain.User
import com.peludosteam.ismarket.service.UserServices
import com.peludosteam.ismarket.service.UserServicesImpl

interface UserRepository {
    suspend fun createUser(user: User)
    suspend fun getCurrentUser(): User?
    suspend fun getAllUsers(): List<User?>

}

class UserRepositoryImpl(
    val userServices: UserServices = UserServicesImpl()
) : UserRepository {
    override suspend fun createUser(user: User) {
        userServices.createUser(user)
    }

    override suspend fun getCurrentUser(): User? {
        val currentUser = Firebase.auth.currentUser
        return if (currentUser != null) {
            userServices.getUserById(currentUser.uid)
        } else {
            null
        }
    }

    override suspend fun getAllUsers(): List<User?> {
        return userServices.getAllUsers()
    }
}