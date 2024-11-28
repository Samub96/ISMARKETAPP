package com.peludosteam.ismarket.domain
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.domain.Purchase

class Purchase (

    val id: String = "",
    val productName: String = "",
    val quantity: Int = 0,
    val totalPrice: Double = 0.0,
    val userId: String = "",
    val purchaseDate: String = ""
)

