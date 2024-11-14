package com.peludosteam.ismarket.viewmode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.Domain.Product
import com.peludosteam.ismarket.repository.CarritoRepository
import kotlinx.coroutines.launch

class CarritoViewMode( val carritoRepository: CarritoRepository) : ViewModel() {

    private val _cartProducts = MutableLiveData<List<Product>>()
    val cartProducts: LiveData<List<Product>> = _cartProducts

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    init {
        loadCartProducts()
    }

    private fun loadCartProducts() {
        val userID = Firebase.auth.currentUser?.uid
        if (userID != null) {
            viewModelScope.launch {
                val products = carritoRepository.getCartProducts(userID) ?: emptyList()
                _cartProducts.value = products
                updateTotalPrice(products)
            }
        }
    }

    private fun updateTotalPrice(products: List<Product>) {
        _totalPrice.value = products.sumOf { it.price }
    }

    fun addProductToCart(product: Product) {
        val userID = Firebase.auth.currentUser?.uid
        if (userID != null) {
            viewModelScope.launch {
                carritoRepository.addProduct(product)
                loadCartProducts()  // Recarga el carrito para reflejar los cambios
            }
        }
    }

    fun removeProductFromCart(productId: String) {
        val userID = Firebase.auth.currentUser?.uid
        if (userID != null) {
            viewModelScope.launch {
                carritoRepository.removeProduct(userID, productId)
                loadCartProducts()
            }
        }
    }

    fun updateProductQuantity(productId: String, quantity: Int) {
        val userID = Firebase.auth.currentUser?.uid
        if (userID != null) {
            viewModelScope.launch {
                carritoRepository.updateProductQuantity(userID, productId, quantity)
                loadCartProducts()
            }
        }
    }

    fun checkout() {
        // Lógica para procesar el pago o finalizar la compra
    }
}
