package com.peludosteam.ismarket.viewmode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class ProductsViewModel : ViewModel() {

    val productList : MutableLiveData<List<Product>> = MutableLiveData(listOf())

    //networking solo en segundo plano
    //UI solo en el hilo principal
    fun downloadData(){
        viewModelScope.launch(Dispatchers.IO) {
            val url = URL("https://fakestoreapi.com/products")
            //val prod=
            val client=url.openConnection() as HttpURLConnection
            client.requestMethod= "GET"
            val json= client.inputStream.bufferedReader().readText()
            val type= Array<Product>::class.java
            val data= Gson().fromJson(json, type)
            withContext(Dispatchers.Main){
                //puedo cambiar la UI
                productList.value = data.toList()
            }
        }
    }
}

data class Product(
    val id:Int,
    val title:String,
    val price: Double,
    val description: String,
    val category: String,
    val image:String,
    val rating:Rate
)
data class Rate(
    val rate:Double,
    val count: Int
)