package com.peludosteam.ismarket.viewmode

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.peludosteam.ismarket.domain.Product

class PedidoViewModel : ViewModel() {

    // Estado del pedido
    var orderStatus by mutableStateOf("En espera") // Estado inicial del pedido
        private set

    // Tiempo estimado de entrega
    var estimatedDeliveryTime by mutableStateOf("Calculando...")
        private set

    // Ubicación del repartidor (esto podría ser una cadena con la ubicación o las coordenadas)
    var deliveryLocation by mutableStateOf("Cargando ubicación...")
        private set

    // Lista de productos del pedido
    var products by mutableStateOf(listOf<Product>())
        private set

    // Función para actualizar el estado del pedido
    fun updateOrderStatus(status: String) {
        orderStatus = status
    }

    // Función para actualizar el tiempo estimado de entrega
    fun updateEstimatedDeliveryTime(time: String) {
        estimatedDeliveryTime = time
    }

    // Función para actualizar la ubicación del repartidor
    fun updateDeliveryLocation(location: String) {
        deliveryLocation = location
    }

    // Función para actualizar los productos del pedido
    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
    }

    // Función para simular la obtención de datos de un pedido desde una API o Firebase
    fun fetchPedidoData(orderId: String) {
        // Este método debería contener la lógica para obtener los datos de un pedido de un backend o Firebase
        // Aquí colocamos datos ficticios como ejemplo.

        // Ejemplo de actualización de datos
        updateOrderStatus("En camino")
        updateEstimatedDeliveryTime("15 minutos")
        updateDeliveryLocation("Av. Siempre Viva 123")
        updateProducts(
            listOf(
                Product("Empanadas", "$1,900", 6),
                Product("Bebidas", "$500", 2)
            )
        )
    }
}