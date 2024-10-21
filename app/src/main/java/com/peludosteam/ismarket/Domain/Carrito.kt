package com.peludosteam.ismarket.Domain

data class Carrito (
    var id: String = "",
    val items: List<Product> = emptyList()
)

