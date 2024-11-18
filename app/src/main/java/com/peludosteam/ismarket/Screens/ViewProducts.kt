package com.peludosteam.ismarket.Screens

import androidx.compose.foundation.layout.PaddingValues
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
import com.peludosteam.ismarket.components.ProductCard
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.viewmode.ProductViewModel

@Composable
fun ViewProducts(navController: NavController, productViewModel: ProductViewModel = viewModel()){
    val productListState by productViewModel.productList.observeAsState()

    //Accion se ejecute al inicio
    LaunchedEffect(true) {
        productViewModel.downloadData()
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {

            item {
                val category="Postres"
                Filtro(innerPadding, productListState, category)
            }
            item {
                val category="Comida y bebidas"
                Filtro(innerPadding, productListState, category)
            }
            item {
                val category="Tecnologia (Accesorios tecnologicos., cargadores, forros celular, etc)"
                Filtro(innerPadding, productListState, category)
            }
            item {
                val category="Tutorias"
                Filtro(innerPadding, productListState, category)
            }
            item {
                val category="Servicios (Limpieza, arreglos y carpinteria, plataformas de streaming, etc)"
                Filtro(innerPadding, productListState, category)
            }
            item {
                val category="Ropa"
                Filtro(innerPadding, productListState, category)
            }
            item {
                val category="Accesorios (Joyeria, pulseras, etc)"
                Filtro(innerPadding, productListState, category)
            }
            item {
                val category="Otros"
                Filtro(innerPadding, productListState, category)
            }
        }
    }

}
@Composable
fun Filtro(innerPadding: PaddingValues, productListState: List<Product>?, catName: String) {
    Text(text = catName)
    LazyRow(modifier = Modifier.padding(innerPadding)) {
        productListState?.let { list->
            items(list){ product ->
                if(product.categoryName==catName){
                                ProductCard(product= product)
                     }
            }
        }
    }
}
