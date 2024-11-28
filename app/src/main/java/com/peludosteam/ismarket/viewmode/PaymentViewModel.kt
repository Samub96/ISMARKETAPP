package com.peludosteam.ismarket.viewmode

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.peludosteam.ismarket.domain.Product
import kotlinx.coroutines.tasks.await
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class PaymentMethod(val id: String, val name: String)
data class DeliveryMethod(val id: String, val name: String)

class PaymentViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    var totalAmount =0

    var selectedPaymentMethod by mutableStateOf(PaymentMethod("card", "Card ending in •3239"))
        private set

    var selectedDeliveryMethod by mutableStateOf(DeliveryMethod("home_delivery", "Entrega a domicilio"))
        private set



    fun setPaymentMethod(paymentMethod: PaymentMethod) {
        selectedPaymentMethod = paymentMethod
    }

    fun setDeliveryMethod(deliveryMethod: DeliveryMethod) {
        selectedDeliveryMethod = deliveryMethod
    }
    fun setAmount(price:Int){
        totalAmount=price
    }


    fun savePaymentToFirebase(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val paymentData = mapOf(
            "paymentMethodId" to selectedPaymentMethod.id,
            "paymentMethodName" to selectedPaymentMethod.name,
            "deliveryMethodId" to selectedDeliveryMethod.id,
            "deliveryMethodName" to selectedDeliveryMethod.name,
            "totalAmount" to totalAmount,
            "timestamp" to System.currentTimeMillis()

        )
        Log.d("Subida firebase", "totalAmount $totalAmount")

        db.collection("payments")
            .add(paymentData)
            .addOnSuccessListener {
                println("Método de pago guardado correctamente")

                onSuccess()
            }
            .addOnFailureListener { exception ->
                println("Error al guardar el método de pago: ${exception.message}")
                onError(exception.message ?: "Error desconocido")
            }
    }

}
