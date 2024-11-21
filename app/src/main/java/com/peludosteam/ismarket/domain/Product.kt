package com.peludosteam.ismarket.domain

data class Product(
    var id: String = "",
    var name: String = "",
    var price: Double = 0.0,
    var description: String = "",
    val imageRes: String = "",
    var stock: Int = 0,
    var categoryName: String = "",
    var userId: String = "" // Agregamos el campo para almacenar el ID del usuario que creó el producto
)

