package com.example.internetshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internetshop.data.model.Category
import com.example.internetshop.data.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository = CategoryRepository()) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val categoriesList = categoryRepository.getCategories()
                _categories.value = categoriesList
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
