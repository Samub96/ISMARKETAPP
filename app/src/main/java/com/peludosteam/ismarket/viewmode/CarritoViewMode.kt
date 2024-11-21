package com.peludosteam.ismarket.viewmode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.repository.CarritoRepository
import com.peludosteam.ismarket.repository.CarritoRepositoryImpl
import kotlinx.coroutines.launch

class CarritoViewMode( val carritoRepository: CarritoRepository) : ViewModel() {

    private val _cartProducts = MutableLiveData<List<Product>>()
    val cartProducts: LiveData<List<Product>> = _cartProducts

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> = _totalPrice

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
        val products = _cartProducts.value ?: emptyList()
        val userID = Firebase.auth.currentUser?.uid
        if (products.isNotEmpty() && userID != null) {
            viewModelScope.launch {
                try {
                    val paymentSuccessful =  true // processPayment(products, _totalPrice.value ?: 0)
                    if (paymentSuccessful) {
                        carritoRepository.clearCart(userID)
                        _cartProducts.value = emptyList()
                        _totalPrice.value = 0
                    } else {
                        // Notificar al usuario que el pago falló
                    }
                } catch (e: FirebaseFirestoreException) {
                    // Manejar errores de Firebase
                }  catch (e: Exception) {
                    // Manejar errores generales
                }
            }
        } else {
            // Notificar al usuario que el carrito está vacío o no está autenticado
        }
    }


}
