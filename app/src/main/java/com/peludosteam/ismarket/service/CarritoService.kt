package com.peludosteam.ismarket.service

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.Domain.Carrito
import com.peludosteam.ismarket.Domain.Product
import kotlinx.coroutines.tasks.await
import java.util.UUID


interface CarritoService {
    suspend fun getCartProducts(id: String): List<Product>
    suspend fun addProduct(userID: String, carritoProduct: Product)
    suspend fun removeProduct(productId: String)
    suspend fun updateProductQuantity(productId: String, quantity: Int)
}

class CarritoServiceImpl : CarritoService {
    override suspend fun getCartProducts(id: String): List<Product> {

        val normalizedProductList = Firebase.firestore
            .collection("User")
            .document(id)
            .collection("ShoppingCar")
            .get().await()

        val productList = normalizedProductList.documents.map { normalizedProduct ->
            Product(
                normalizedProduct.get("productId").toString(),
                normalizedProduct.get("Name").toString(),
                normalizedProduct.get("Price").toString().toDouble(),
            )
        }
        return productList
    }

    override suspend fun addProduct(userID: String, product: Product) {
        val normalizedProduct = hashMapOf(
            "id" to UUID.randomUUID().toString(),
            "productId" to product.id,
            "Name" to product.name,
            "Price" to product.price
        )

        Firebase.firestore.collection("User").document(userID)
            .collection("ShoppingCar")
            .document(normalizedProduct["id"].toString())
            .set(normalizedProduct).await()
    }

    override suspend fun removeProduct(productId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateProductQuantity(productId: String, quantity: Int) {
        TODO("Not yet implemented")
    }


}
