package com.example.internetshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.internetshop.ui.MainScreen
import com.example.internetshop.ui.theme.InternetShopTheme
import com.example.internetshop.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Инициализируем ProductViewModel
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        setContent {
            InternetShopTheme {
                MainScreen(productViewModel = productViewModel)
            }
        }
    }
}
