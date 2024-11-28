package com.peludosteam.ismarket.viewmode

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.repository.ProductRepository
import com.peludosteam.ismarket.repository.ProductRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProductViewModel(
    private val productRepository:ProductRepository= ProductRepositoryImpl(),

    ) : ViewModel() {

    val productList: MutableLiveData<List<Product>> = MutableLiveData(listOf())
    private val firestore = FirebaseFirestore.getInstance()

    fun updateProductStock(productId: String, newStock: Int) {
        firestore.collection("products") // Asegúrate de usar la colección correcta
            .document(productId)
            .update("stock", newStock)
            .addOnSuccessListener {
                Log.d("ProductViewModel", "Stock actualizado exitosamente.")
            }
            .addOnFailureListener { exception ->
                Log.e("ProductViewModel", "Error al actualizar el stock: ${exception.message}")
            }
    }


    fun downloadData() {
        viewModelScope.launch(Dispatchers.IO) {
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

    fun deleteProduct(id: String) {
        viewModelScope.launch {
            try {
                // Eliminar el producto de Firestore
                val productRef = FirebaseFirestore.getInstance()
                    .collection("products")
                    .document(id)  // Usamos el campo `id`

                productRef.delete().await() // Elimina el producto de Firestore

                Log.d("ProductViewModel", "Producto eliminado exitosamente de Firestore.")

                // Actualizar los datos después de la eliminación
                downloadData()  // Vuelve a descargar los datos actualizados después de la eliminación

            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error al eliminar producto de Firestore: ${e.message}")
            }
        }
    }




}
