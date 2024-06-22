package com.example.internetshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internetshop.data.model.Product
import com.example.internetshop.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(private val productRepository: ProductRepository = ProductRepository()) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> get() = _selectedProduct

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val products = productRepository.getProducts()
                _products.value = products
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun loadProductsByCategory(categoryId: String) {
        viewModelScope.launch {
            try {
                val products = productRepository.getProductsByCategory(categoryId)
                _products.value = products
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun getProductById(productId: String) {
        viewModelScope.launch {
            _selectedProduct.value = productRepository.getProductById(productId)
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            val success = productRepository.addProduct(product)
            if (success) {
                loadProducts()
            }
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            val success = productRepository.updateProduct(product)
            if (success) {
                loadProducts()
            }
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            val success = productRepository.deleteProduct(productId)
            if (success) {
                loadProducts()
            }
        }
    }
}
