package com.peludosteam.ismarket.viewmode

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.repository.AuthRepository
import com.peludosteam.ismarket.repository.AuthRepositoryImpl
import com.peludosteam.ismarket.repository.ProductRepository
import com.peludosteam.ismarket.repository.ProductRepositoryImpl
import com.peludosteam.ismarket.service.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class ProductViewModel(
    val productRepository:ProductRepository= ProductRepositoryImpl()
) : ViewModel() {

    val productList : MutableLiveData<List<Product>> = MutableLiveData(listOf())

    fun downloadData() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val snapshot = withContext(Dispatchers.IO) {
                    Firebase.firestore.collection("products").get().await()
                }
                val products = snapshot.documents.map { document ->
                    document.toObject(Product::class.java)!!
                }
                withContext(Dispatchers.Main) {
                    // Actualizar la UI con la lista de productos
                    productList.value = products
                }
            } catch (e: Exception) {
                // Manejo de errores
                Log.e("FirestoreError", "Error obteniendo productos: ", e)
            }
        }
    }




}
