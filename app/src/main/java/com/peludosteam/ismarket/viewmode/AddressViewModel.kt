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
            details = "Por favor espera..."
        )
    )
        private set

    private val db = FirebaseFirestore.getInstance()

    fun updateDeliveryMethod(method: String) {
        deliveryMethod = method
    }

    fun updateAddressDetails(newDetails: AddressDetails) {
        addressDetails = newDetails
    }

    fun listenForAddressUpdates() {
        // Escucha en tiempo real para obtener la dirección más reciente
        db.collection("Addresses")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(1) // Aseguramos que solo obtengamos el documento más reciente
            .addSnapshotListener { documents, error ->
                if (error != null) {
                    println("Error al obtener la dirección: ${error.message}")
                    return@addSnapshotListener
                }

                // Si hay documentos, actualizamos la UI con los más recientes
                documents?.forEach { document ->
                    val location = "Edificio: ${document.getString("building") ?: "Sin edificio"}"
                    val details = "Piso: ${document.getString("floor") ?: "Sin piso"} Salón: ${
                        document.getString("room") ?: "Sin habitación"
                    }"
                    updateAddressDetails(AddressDetails(location, details))

                }
            }
    }
}

data class AddressDetails(
    val location: String,
    val details: String,
)