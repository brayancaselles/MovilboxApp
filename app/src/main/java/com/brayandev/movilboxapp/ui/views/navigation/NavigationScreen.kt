package com.brayandev.movilboxapp.ui.views.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brayandev.movilboxapp.ui.views.products.ProductsScreen
import com.brayandev.movilboxapp.ui.views.products.ProductsViewModel

@Composable
fun NavigationScreen() {
    val navigationController = rememberNavController()

    NavHost(navController = navigationController, startDestination = Routes.Products.route) {
        composable(Routes.SplashScreen.route) {
        }
        composable(Routes.Products.route) {
            val viewModel = hiltViewModel<ProductsViewModel>()
            ProductsScreen(productsViewModel = viewModel, navController = navigationController)
        }
        composable(Routes.ProductDetail.route) {
        }
    }
}
