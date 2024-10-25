package com.peludosteam.ismarket.service


import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.Domain.User
import kotlinx.coroutines.tasks.await

interface UserServices {
    suspend fun createUser(user: User)
    suspend fun getUserById(id:String): User?

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


}