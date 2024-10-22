package com.peludosteam.ismarket.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.domain.Product
import kotlinx.coroutines.tasks.await

interface ProductRepository {
    suspend fun getAllProducts(): List<Product>
    suspend fun getProductById(id: String): Product?
    suspend fun addProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(id: String)

}

class ProductRepositoryImpl : ProductRepository {

    private val productCollection = Firebase.firestore.collection("products")

    override suspend fun getAllProducts(): List<Product> {
        return try {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                val snapshot = productCollection
                    .whereEqualTo("userId", currentUser.uid)  // Solo productos del usuario actual
                    .get()
                    .await()
                snapshot.toObjects(Product::class.java)
            } else {
                emptyList() // Retorna una lista vacía si no hay un usuario logueado
            }
        } catch (e: Exception) {
            emptyList() // En caso de error
        }
    }

    override suspend fun getProductById(id: String): Product? {
        return try {
            val document = productCollection.document(id).get().await()
            document.toObject(Product::class.java)
        } catch (e: Exception) {
            null // Retorna null si hay algún error
        }
    }

    override suspend fun addProduct(product: Product) {
        try {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                val productWithUser = product.copy(id = product.id, userId = currentUser.uid)
                productCollection.document(productWithUser.id).set(productWithUser).await()
            }
        } catch (e: Exception) {
            // Manejo de error si ocurre al agregar producto
        }
    }

    override suspend fun updateProduct(product: Product) {
        try {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null && product.userId == currentUser.uid) {
                productCollection.document(product.id).set(product).await()
            }
        } catch (e: Exception) {
            // Manejo de error
        }
    }

    override suspend fun deleteProduct(id: String) {
        try {
            val currentUser = Firebase.auth.currentUser
            val product = getProductById(id)
            if (currentUser != null && product?.userId == currentUser.uid) {
                productCollection.document(id).delete().await()
            }
        } catch (e: Exception) {
            // Manejo de error
        }
    }
}
