package com.example.internetshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.internetshop.data.model.Product

class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<List<Product>>(emptyList())
    val cartItems: LiveData<List<Product>> = _cartItems

    private val _totalAmount = MutableLiveData(0.0)
    val totalAmount: LiveData<Double> = _totalAmount

    fun addToCart(product: Product) {
        val updatedCart = _cartItems.value?.toMutableList() ?: mutableListOf()
        updatedCart.add(product)
        _cartItems.value = updatedCart
        updateTotalAmount()
    }

    fun removeFromCart(product: Product) {
        val updatedCart = _cartItems.value?.toMutableList() ?: mutableListOf()
        updatedCart.remove(product)
        _cartItems.value = updatedCart
        updateTotalAmount()
    }

    private fun updateTotalAmount() {
        val amount = _cartItems.value?.sumByDouble { it.Price } ?: 0.0
        _totalAmount.value = amount
    }
}
