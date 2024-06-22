package com.example.internetshop.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.example.internetshop.data.model.Category
import com.example.internetshop.ui.components.CategoryCard
import com.example.internetshop.viewmodel.CategoryViewModel

@Composable
fun CategoriesScreen(categoryViewModel: CategoryViewModel = viewModel(), onCategoryClick: (String) -> Unit) {
    LaunchedEffect(key1 = true) {
        categoryViewModel.loadCategories()
    }

    val categories by categoryViewModel.categories.collectAsState(initial = emptyList())
    val error by categoryViewModel.error.collectAsState()

    Column {
        Text(
            text = "Категории",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        if (error != null) {
            Text(
                text = "Ошибка при загрузке категорий: $error",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn {
                items(categories) { category ->
                    CategoryCard(
                        category = category,
                        onCategoryClick = { categoryId ->
                            onCategoryClick(categoryId.toString())
                        }
                    )
                }
            }
        }
    }
}
