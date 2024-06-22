package com.example.internetshop.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.internetshop.R
import com.example.internetshop.ui.screens.*
import com.example.internetshop.viewmodel.ProductViewModel
import com.example.internetshop.viewmodel.CategoryViewModel

@Composable
fun MainScreen(productViewModel: ProductViewModel = viewModel(), categoryViewModel: CategoryViewModel = viewModel()) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    productViewModel = productViewModel,
                    onProductClick = { product ->
                        navController.navigate("product_detail/${product.id}")
                    }
                )
            }
            composable(
                route = "product_detail/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")
                ProductDetailScreen(productId = productId, productViewModel = productViewModel)
            }
            composable("categories") {
                CategoriesScreen(
                    categoryViewModel = categoryViewModel,
                    onCategoryClick = { categoryId ->
                        productViewModel.loadProductsByCategory(categoryId)
                        navController.navigate("home")
                    }
                )
            }
            composable("add_product") {
                AddProductScreen(
                    onProductAdded = {
                        navController.popBackStack()
                        productViewModel.loadProducts()
                    }
                )
            }
            composable("cart") { CartScreen() }
            composable("profile") { ProfileScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Categories,
        BottomNavItem.AddProduct,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title)
                    )
                },
                label = { Text(stringResource(id = item.title)) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                }
            )
        }
    }
}

sealed class BottomNavItem(var title: Int, var icon: Int, var route: String) {
    object Home : BottomNavItem(R.string.home, R.drawable.ic_home, "home")
    object Categories : BottomNavItem(R.string.categories, R.drawable.ic_categories, "categories")
    object AddProduct : BottomNavItem(R.string.add_product, R.drawable.ic_add_circle, "add_product")
    object Cart : BottomNavItem(R.string.cart, R.drawable.ic_cart, "cart")
    object Profile : BottomNavItem(R.string.profile, R.drawable.ic_account_circle, "profile")
}
