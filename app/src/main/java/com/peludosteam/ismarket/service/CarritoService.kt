package com.peludosteam.ismarket.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.peludosteam.ismarket.Domain.Carrito
import com.peludosteam.ismarket.Domain.Product
import kotlinx.coroutines.tasks.await

class CarritoService {
    private val firestore = FirebaseFirestore.getInstance()
    private val cartCollection = firestore.collection("Carrito") // Colección donde se guardan los carritos

    suspend fun getCartItems(): List<Product> {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return emptyList()
        val cartRef = cartCollection.document(userId)
        val document = cartRef.get().await()

        return if (document.exists()) {
            document.toObject(Carrito::class.java)?.items ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun addProductToCart(product: Product) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val cartRef = cartCollection.document(userId)

        cartRef.update("items", FieldValue.arrayUnion(product)).await()
    }

    suspend fun removeProductFromCart(product: Product) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val cartRef = cartCollection.document(userId)

        cartRef.update("items", FieldValue.arrayRemove(product)).await()
    }

    suspend fun clearCart() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val cartRef = cartCollection.document(userId)

        cartRef.update("items", emptyList<Any>()).await()
    }
}
