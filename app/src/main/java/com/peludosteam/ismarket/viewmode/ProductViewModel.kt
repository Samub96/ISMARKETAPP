package com.peludosteam.ismarket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.service.ProductService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val productService: ProductService) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> get() = _selectedProduct

    init {
        fetchAllProducts()
    }

    fun fetchAllProducts() {
        viewModelScope.launch {
            val productList = productService.getAllProducts()
            _products.value = productList
        }
    }

    fun selectProduct(id: String) {
        viewModelScope.launch {
            val product = productService.getProductById(id)
            _selectedProduct.value = product
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            productService.addProduct(product)
            fetchAllProducts()
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            productService.updateProduct(product)
            fetchAllProducts()
        }
    }

    fun deleteProduct(id: String) {
        viewModelScope.launch {
            productService.deleteProduct(id)
            fetchAllProducts()
        }
    }




}
