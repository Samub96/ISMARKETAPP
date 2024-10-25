package com.peludosteam.ismarket.Screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.listasapp.components.ProductCard
import com.peludosteam.ismarket.viewmode.ProductsViewModel
import com.peludosteam.ismarket.viewmode.SignupViewModel

@Composable
fun ViewProducts(navController: NavController, productsViewModel: ProductsViewModel = viewModel()){
    val productListState by productsViewModel.productList.observeAsState()
    //Accion se ejecute al inicio
    LaunchedEffect(true) {
        productsViewModel.downloadData()
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {

            item {
                //Electronics(innerPadding, productListState)
            }
            item {
                //Comida(innerPadding, productListState)
            }
            item{
                Text(text = "Joyeria")
                LazyRow(modifier = Modifier.padding(innerPadding)) {
                    productListState?.let { list->
                        items(list){ product ->
                            if(product.category=="jewelery"){
                                ProductCard(product= product)

                            }
                            //Text(text = product.title)
                        }
                    }
                }
            }



        }
    }

}