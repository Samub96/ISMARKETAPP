package com.peludosteam.ismarket.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.service.CarritoService
import com.peludosteam.ismarket.service.CarritoServiceImpl

interface CarritoRepository {
    suspend fun getCartProducts(userID: String): List<Product>?
    suspend fun addProduct(product: Product)
    suspend fun removeProduct(userID: String, productId: String)
    suspend fun updateProductQuantity(userID: String, productId: String, quantity: Int)
    suspend fun clearCart(userID: String)
}

class CarritoRepositoryImpl(
    private val carritoService: CarritoService = CarritoServiceImpl()
) : CarritoRepository {

    private val userID: String?
        get() = Firebase.auth.currentUser?.uid

    override suspend fun getCartProducts(userID: String): List<Product>? {
        return userID?.let { carritoService.getCartProducts(it) }
    }

    override suspend fun addProduct(product: Product) {
        userID?.let { carritoService.addProduct(it, product) }
    }

    override suspend fun removeProduct(userID: String, productId: String) {
        carritoService.removeProduct(userID, productId)
    }
    override suspend fun updateProductQuantity(userID: String, productId: String, quantity: Int) {
        carritoService.updateProductQuantity(userID, productId, quantity)
    }

    override suspend fun clearCart(userID: String) {
        carritoService.clearCart(userID)
    }

}
