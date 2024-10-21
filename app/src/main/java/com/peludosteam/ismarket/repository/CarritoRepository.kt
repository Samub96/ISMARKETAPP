package com.peludosteam.ismarket.repository

import androidx.compose.runtime.State
import com.peludosteam.ismarket.Domain.Product
import com.peludosteam.ismarket.service.CarritoService


interface CarritoRepository {
    suspend fun getCartItems(): State<List<Product>>
    suspend fun addProduct(product: Product)
    suspend fun removeProduct(product: Product)
    suspend fun clearCart()
    suspend fun getTotalPrice(): Double
}
class CarritoRepositoryImp (
    private val cartService: CarritoService = CarritoService()
) : CarritoRepository {
    override suspend fun getCartItems(): State<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun addProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun removeProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCart() {
        TODO("Not yet implemented")
    }

    override suspend fun getTotalPrice(): Double {
        TODO("Not yet implemented")
    }


}