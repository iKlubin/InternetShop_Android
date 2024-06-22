package com.example.internetshop.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.internetshop.R
import com.example.internetshop.data.model.Product
import com.example.internetshop.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(cartViewModel: CartViewModel = viewModel()) {
    val cartItems by cartViewModel.cartItems.observeAsState(emptyList())
    val totalAmount by cartViewModel.totalAmount.observeAsState(0.0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.cart)) },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation back */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp) // Additional horizontal padding
            ) {
                if (cartItems.isEmpty()) {
                    Text(
                        text = "Your cart is empty",
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                } else {
                    LazyColumn {
                        items(cartItems) { cartItem ->
                            CartItemRow(cartItem = cartItem)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Total: $totalAmount",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(
                    onClick = { /* Handle checkout */ },
                    enabled = cartItems.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Checkout")
                }
            }
        }
    )
}

@Composable
fun CartItemRow(cartItem: Product) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = cartItem.Name,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "₽${cartItem.Price}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}