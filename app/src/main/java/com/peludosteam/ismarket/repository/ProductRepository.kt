package com.peludosteam.ismarket.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
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

    // Cambia para acceder a la subcolección de productos del usuario
    private fun getUserProductCollection(userId: String) =
        Firebase.firestore.collection("User").document(userId).collection("Product")
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun getAllProducts(): List<Product> {
        return try {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                val snapshot = getUserProductCollection(currentUser.uid)
                    .get()
                    .await()
                snapshot.toObjects(Product::class.java)
            } else {
                emptyList() // Retorna lista vacía si no hay un usuario logueado
            }
        } catch (e: Exception) {
            emptyList() // En caso de error
        }
    }

    override suspend fun getProductById(id: String): Product? {
        return try {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                val document = getUserProductCollection(currentUser.uid)
                    .document(id)
                    .get()
                    .await()
                document.toObject(Product::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null // Retorna null si hay algún error
        }
    }

    override suspend fun addProduct(product: Product) {
        try {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                val productWithUser = product.copy(id = product.id, userId = currentUser.uid)
                getUserProductCollection(currentUser.uid)
                    .document(productWithUser.id)
                    .set(productWithUser)
                    .await()
            }
        } catch (e: Exception) {
            // Manejo de error si ocurre al agregar producto
        }
    }

    override suspend fun updateProduct(product: Product) {
        try {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null && product.userId == currentUser.uid) {
                getUserProductCollection(currentUser.uid)
                    .document(product.id)
                    .set(product)
                    .await()
            }
        } catch (e: Exception) {
            // Manejo de error
        }
    }

    override suspend fun deleteProduct(productId: String) {
        try {
            // Obtén el usuario actual
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                // Ruta correcta al documento de producto dentro de la subcolección "Product"
                firestore.collection("users") // Asegúrate de que la colección "users" esté bien nombrada
                    .document(currentUser.uid) // Obtiene el documento del usuario actual
                    .collection("Product") // Subcolección donde están los productos
                    .document(productId) // Documento específico del producto
                    .delete() // Elimina el producto
                    .addOnSuccessListener {
                        Log.d("ProductRepositoryImpl", "Producto eliminado con éxito.")
                    }
                    .addOnFailureListener { exception ->
                        Log.e("ProductRepositoryImpl", "Error al eliminar el producto: ${exception.message}")
                    }
            } else {
                Log.e("ProductRepositoryImpl", "No hay un usuario autenticado.")
            }
        } catch (e: Exception) {
            Log.e("ProductRepositoryImpl", "Error al eliminar el producto: ${e.message}")
        }
    }
}
