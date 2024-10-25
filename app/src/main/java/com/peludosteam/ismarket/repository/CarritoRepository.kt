package com.peludosteam.ismarket.repository

import androidx.compose.runtime.State
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.Domain.Product
import com.peludosteam.ismarket.service.CarritoService
import com.peludosteam.ismarket.service.CarritoServiceImpl


interface CarritoRepository {
    suspend fun getCartProducts(userID:String): List<Product>?
    suspend fun addProduct(product: Product)
    suspend fun removeProduct(userID:String, productId: String)
    suspend fun updateProductQuantity(userID:String, productId: String, quantity: Int)
}

class CarritoRepositoryImp(
    val carritoService: CarritoService = CarritoServiceImpl()
) : CarritoRepository {
    override suspend fun getCartProducts(userID: String): List<Product>? {
        TODO("Not yet implemented")
    }

    override suspend fun addProduct(product: Product) {
        carritoService.addProduct(
            Firebase.auth.currentUser?.uid ?: "",
            product
        )
    }

    override suspend fun removeProduct(userID: String, productId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateProductQuantity(userID: String, productId: String, quantity: Int) {
        TODO("Not yet implemented")
    }

}