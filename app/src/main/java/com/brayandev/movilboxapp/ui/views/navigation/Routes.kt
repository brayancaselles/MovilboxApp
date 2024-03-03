package com.brayandev.movilboxapp.ui.views.navigation

const val PRODUCT_ID = "productId"

sealed class Routes(val route: String) {
    object SplashScreen : Routes("splash")
    object Products : Routes(route = "products")
    object ProductDetail : Routes(route = "productDetail/{$PRODUCT_ID}") {
        fun createRoute(productId: Int): String {
            return "productDetail/$productId"
        }
    }
}
