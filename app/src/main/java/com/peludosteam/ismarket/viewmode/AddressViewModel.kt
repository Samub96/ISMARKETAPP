package com.peludosteam.ismarket.viewmode

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query

class AddressViewModel : ViewModel() {
    var deliveryMethod by mutableStateOf("Entrega a domicilio")
        private set
    var addressDetails by mutableStateOf(
        AddressDetails(
            location = "Cargando...",
            room = "Cargando...",
            floor = "Cargando..."
        )
    )

        private set

    var productPrice by mutableStateOf("Cargando precio...")
        private set

    private val db = FirebaseFirestore.getInstance()

    fun updateDeliveryMethod(method: String) {
        deliveryMethod = method
    }

    fun updateAddressDetails(newDetails: AddressDetails) {
        addressDetails = newDetails
    }

    fun listenForAddressUpdates() {
        db.collection("Addresses")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(1)
            .addSnapshotListener { documents, error ->
                if (error != null) {
                    println("Error al obtener la dirección: ${error.message}")
                    return@addSnapshotListener
                }

                documents?.forEach { document ->
                    val location = "Edificio: ${document.getString("building") ?: "Sin edificio"}"
                    val floor = document.getString("floor") ?: "Sin piso"
                    val room = document.getString("room") ?: "Sin habitación"

                    // Aquí creas un nuevo AddressDetails con los valores correspondientes
                    val newAddressDetails = AddressDetails(location, floor, room)

                    // Actualizas los detalles con el nuevo AddressDetails
                    updateAddressDetails(newAddressDetails)
                }
            }
    }

    fun listenForProductPrice() {
        println("Iniciando escucha de precios en Firestore...")
        db.collection("products")
            .orderBy("price", Query.Direction.DESCENDING)
            .limit(1)
            .addSnapshotListener { documents, error ->
                if (error != null) {
                    println("Error al obtener el precio del producto: ${error.message}")
                    updateProductPrice("Error al cargar precio")
                    return@addSnapshotListener
                }

                if (documents == null || documents.isEmpty) {
                    println("No se encontraron productos en la colección.")
                    updateProductPrice("Precio no disponible")
                    return@addSnapshotListener
                }

                for (document in documents) {
                    val price = document.getDouble("price") ?: 0.0
                    println("Precio obtenido desde Firestore: $price")
                    updateProductPrice("$${price}")
                }
            }
    }

    private fun updateProductPrice(price: String) {
        productPrice = price
    }
}

data class AddressDetails(
    val location: String,
    val room:String,
    val floor: String

)