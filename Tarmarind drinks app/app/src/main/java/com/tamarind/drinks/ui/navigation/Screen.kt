package com.tamarind.drinks.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    
    object Home : Screen("home")
    object Category : Screen("category/{categoryId}") {
        fun createRoute(categoryId: String) = "category/$categoryId"
    }
    object ProductDetail : Screen("product/{productId}") {
        fun createRoute(productId: String) = "product/$productId"
    }
    object Search : Screen("search")
    
    object Cart : Screen("cart")
    object Checkout : Screen("checkout")
    object OrderConfirmation : Screen("order_confirmation/{orderId}") {
        fun createRoute(orderId: String) = "order_confirmation/$orderId"
    }
    
    object Orders : Screen("orders")
    object OrderDetail : Screen("order/{orderId}") {
        fun createRoute(orderId: String) = "order/$orderId"
    }
    
    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")
    object Addresses : Screen("addresses")
    object AddAddress : Screen("add_address")
    object NotificationSettings : Screen("notification_settings")
    
    object AdminDashboard : Screen("admin/dashboard")
    object AdminOrders : Screen("admin/orders")
    object AdminProducts : Screen("admin/products")
    object AdminProductForm : Screen("admin/product_form/{productId}") {
        fun createRoute(productId: String?) = "admin/product_form/${productId ?: "new"}"
    }
}
