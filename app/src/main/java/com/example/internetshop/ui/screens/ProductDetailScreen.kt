package com.example.internetshop.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.internetshop.viewmodel.ProductViewModel

@Composable
fun ProductDetailScreen(productId: String?, productViewModel: ProductViewModel = viewModel()) {
    LaunchedEffect(productId) {
        productId?.let { productViewModel.getProductById(it) }
    }

    val product by productViewModel.selectedProduct.collectAsState()

    product?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = it.Name, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.Description, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "₽${it.Price}", style = MaterialTheme.typography.headlineSmall)
        }
    } ?: run {
        Text(text = "Product not found", style = MaterialTheme.typography.headlineMedium)
    }
}
