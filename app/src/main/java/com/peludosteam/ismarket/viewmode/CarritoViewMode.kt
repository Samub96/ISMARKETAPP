package com.peludosteam.ismarket.viewmode

import androidx.annotation.DrawableRes
import com.peludosteam.ismarket.R

sealed class CarritoViewMode(
    @DrawableRes val image : Int,
    val title:String,
    val description :String
) {
    data object Carrito : CarritoViewMode(
        image = R.drawable.carrito,
        title = "El carrito esta vacio",
        description = "Comienza tu pedido"
    )




}