package com.tamarind.drinks.data.repository

import com.tamarind.drinks.data.local.dao.ProductDao
import com.tamarind.drinks.data.local.entity.ProductEntity
import com.tamarind.drinks.data.mock.MockDataProvider
import com.tamarind.drinks.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productDao: ProductDao
) {
    
    suspend fun getAllProducts(): Flow<Resource<List<ProductEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val products = productDao.getAllProducts()
            
            // If database is empty, populate with mock data
            if (products.isEmpty()) {
                val mockProducts = MockDataProvider.getMockProducts()
                productDao.insertProducts(mockProducts)
                emit(Resource.Success(mockProducts))
            } else {
                emit(Resource.Success(products))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to load products"))
        }
    }
    
    suspend fun getFeaturedProducts(): Flow<Resource<List<ProductEntity>>> = flow {
        emit(Resource.Loading())
        try {
            // Ensure database is populated
            val allProducts = productDao.getAllProducts()
            if (allProducts.isEmpty()) {
                val mockProducts = MockDataProvider.getMockProducts()
                productDao.insertProducts(mockProducts)
            }
            
            val featured = MockDataProvider.getFeaturedProducts()
            emit(Resource.Success(featured))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to load products"))
        }
    }
    
    suspend fun getProductsByCategory(category: String): Flow<Resource<List<ProductEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val products = productDao.getProductsByCategory(category)
            
            if (products.isEmpty()) {
                // Populate database if empty
                val mockProducts = MockDataProvider.getMockProducts()
                productDao.insertProducts(mockProducts)
                val categoryProducts = MockDataProvider.getProductsByCategory(category)
                emit(Resource.Success(categoryProducts))
            } else {
                emit(Resource.Success(products))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to load products"))
        }
    }
    
    suspend fun getProductById(productId: String): Flow<Resource<ProductEntity>> = flow {
        emit(Resource.Loading())
        try {
            var product = productDao.getProductById(productId)
            
            if (product == null) {
                // Try to find in mock data
                val mockProducts = MockDataProvider.getMockProducts()
                product = mockProducts.find { it.id == productId }
                
                if (product != null) {
                    productDao.insertProduct(product)
                    emit(Resource.Success(product))
                } else {
                    emit(Resource.Error("Product not found"))
                }
            } else {
                emit(Resource.Success(product))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to load product"))
        }
    }
    
    suspend fun searchProducts(query: String): Flow<Resource<List<ProductEntity>>> = flow {
        emit(Resource.Loading())
        try {
            // Ensure database is populated
            val allProducts = productDao.getAllProducts()
            if (allProducts.isEmpty()) {
                val mockProducts = MockDataProvider.getMockProducts()
                productDao.insertProducts(mockProducts)
            }
            
            val results = MockDataProvider.searchProducts(query)
            emit(Resource.Success(results))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to search products"))
        }
    }
    
    suspend fun getRelatedProducts(productId: String, category: String): Flow<Resource<List<ProductEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val categoryProducts = MockDataProvider.getProductsByCategory(category)
            val related = categoryProducts.filter { it.id != productId }.take(4)
            emit(Resource.Success(related))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to load related products"))
        }
    }
}
