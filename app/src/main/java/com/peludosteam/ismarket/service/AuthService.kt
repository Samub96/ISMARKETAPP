package com.peludosteam.ismarket.service

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface AuthService {
    suspend fun createUser(email: String, password: String)
    suspend fun loginWithEmailAndPassword(email: String, password: String)
    fun getCurrentUser(): FirebaseAuth?
    fun signout()
}

class AuthServiceImpl : AuthService {
    private val auth = Firebase.auth

    override suspend fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun loginWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override fun getCurrentUser(): FirebaseAuth? {
        return auth.currentUser?.let { auth }
    }

    override fun signout() {
        auth.signOut()
    }
}