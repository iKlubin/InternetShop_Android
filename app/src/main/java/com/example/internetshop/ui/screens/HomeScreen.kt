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

import com.example.internetshop.data.model.Product
import com.example.internetshop.ui.components.ProductCard
import com.example.internetshop.viewmodel.ProductViewModel

@Composable
fun HomeScreen(productViewModel: ProductViewModel = viewModel(), onProductClick: (Product) -> Unit) {
    LaunchedEffect(key1 = true) {
        productViewModel.loadProducts()
    }

    val products by productViewModel.products.collectAsState(initial = emptyList())
    val error by productViewModel.error.collectAsState()

    Column {
        Text(
            text = "Главная",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        if (error != null) {
            Text(
                text = "Error loading products: $error",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn {
                items(products) { product ->
                    ProductCard(
                        product = product,
                        onProductClick = onProductClick
                    )
                }
            }
        }
    }
}
