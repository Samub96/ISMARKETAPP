package com.peludosteam.ismarket.repository

import androidx.compose.runtime.State
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.Domain.Product
import com.peludosteam.ismarket.service.CarritoService
import com.peludosteam.ismarket.service.CarritoServiceImpl

interface CarritoRepository {
    suspend fun getCartProducts(userID: String): List<Product>?
    suspend fun addProduct(product: Product)
    suspend fun removeProduct(userID: String, productId: String)
    suspend fun updateProductQuantity(userID: String, productId: String, quantity: Int)
}

class CarritoRepositoryImp(
    private val carritoService: CarritoService = CarritoServiceImpl()
) : CarritoRepository {
    override suspend fun getCartProducts(userID: String): List<Product>? {
        // Llama a CarritoService para obtener los productos en el carrito del usuario
        return carritoService.getCartProducts(userID)
    }

    override suspend fun addProduct(product: Product) {
        // Obtiene el userID desde FirebaseAuth y agrega el producto al carrito
        val userID = Firebase.auth.currentUser?.uid ?: ""
        if (userID.isNotEmpty()) {
            carritoService.addProduct(userID, product)
        }
    }

    override suspend fun removeProduct(userID: String, productId: String) {
        // Llama a CarritoService para eliminar el producto específico del carrito
        carritoService.removeProduct(userID, productId)
    }

    override suspend fun updateProductQuantity(userID: String, productId: String, quantity: Int) {
        // Llama a CarritoService para actualizar la cantidad del producto en el carrito
        carritoService.updateProductQuantity(userID, productId, quantity)
    }
}