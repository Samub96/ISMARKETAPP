package com.peludosteam.ismarket.service
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.repository.ProductRepository
import com.peludosteam.ismarket.repository.ProductRepositoryImpl


interface ProductService {
    suspend fun getAllProducts(): List<Product>
    suspend fun getProductById(id: String): Product?
    suspend fun addProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(id: String)

}

class ProductServiceImpl(
    private val productRepository: ProductRepository = ProductRepositoryImpl()
) : ProductService {



    override suspend fun getAllProducts(): List<Product> {
        return try {
            productRepository.getAllProducts()
        } catch (e: Exception) {
            // En caso de error, devuelve una lista vacía
            emptyList()
        }
    }

    override suspend fun getProductById(id: String): Product? {
        return try {
            productRepository.getProductById(id)
        } catch (e: Exception) {
            // En caso de error, devuelve null
            null
        }
    }

    override suspend fun addProduct(product: Product) {
        if (product.name.isNotEmpty() && product.price > 0) {
            try {
                productRepository.addProduct(product)
            } catch (e: Exception) {
                // Maneja el error al agregar el producto
            }
        }
    }

    override suspend fun updateProduct(product: Product) {
        if (product.id.isNotEmpty()) {
            try {
                productRepository.updateProduct(product)
            } catch (e: Exception) {
                // Maneja el error al actualizar el producto
            }
        }
    }

    override suspend fun deleteProduct(id: String) {
        try {
            productRepository.deleteProduct(id)
        } catch (e: Exception) {
            // Maneja el error al eliminar el producto
        }
    }
}

