package com.peludosteam.ismarket.domain
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.domain.Purchase


data class Purchase(
    val id: String = "",
    val userId: String = "",
    val paymentMethodName: String = "",
    val deliveryMethodName: String = "",
    val totalAmount: Double = 0.0,
    val products: List<Product> = emptyList(), // Lista de productos comprados
    val timestamp: Long = 0L // Fecha de la compra en formato Unix
)


