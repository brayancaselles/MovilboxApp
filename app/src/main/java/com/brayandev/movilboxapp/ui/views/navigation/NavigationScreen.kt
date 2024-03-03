package com.brayandev.movilboxapp.ui.views.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.brayandev.movilboxapp.ui.views.detailProduct.DetailProductScreen
import com.brayandev.movilboxapp.ui.views.detailProduct.DetailProductViewModel
import com.brayandev.movilboxapp.ui.views.products.ProductsScreen
import com.brayandev.movilboxapp.ui.views.products.ProductsViewModel
import com.brayandev.movilboxapp.ui.views.splash.SplashScreen

@Composable
fun NavigationScreen() {
    val navigationController = rememberNavController()

    NavHost(navController = navigationController, startDestination = Routes.SplashScreen.route) {
        composable(Routes.SplashScreen.route) {
            SplashScreen(navController = navigationController)
        }
        composable(Routes.Products.route) {
            val viewModel = hiltViewModel<ProductsViewModel>()
            ProductsScreen(productsViewModel = viewModel, navController = navigationController)
        }
        composable(
            Routes.ProductDetail.route,
            arguments = listOf(navArgument(PRODUCT_ID) { type = NavType.IntType }),
        ) {
            val viewModel = hiltViewModel<DetailProductViewModel>()
            DetailProductScreen(viewModel = viewModel, navController = navigationController)
        }
    }
}
