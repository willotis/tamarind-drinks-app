package com.tamarind.drinks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tamarind.drinks.ui.auth.LoginScreen
import com.tamarind.drinks.ui.auth.RegisterScreen
import com.tamarind.drinks.ui.cart.CartScreen
import com.tamarind.drinks.ui.checkout.CheckoutScreen
import com.tamarind.drinks.ui.checkout.OrderConfirmationScreen
import com.tamarind.drinks.ui.home.HomeScreen
import com.tamarind.drinks.ui.orders.OrderDetailScreen
import com.tamarind.drinks.ui.orders.OrdersScreen
import com.tamarind.drinks.ui.products.CategoryScreen
import com.tamarind.drinks.ui.products.ProductDetailScreen
import com.tamarind.drinks.ui.products.SearchScreen
import com.tamarind.drinks.ui.profile.ProfileScreen
import com.tamarind.drinks.ui.splash.SplashScreen

@Composable
fun TamarindNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Splash & Onboarding
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        // Auth screens
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }

        // Home
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        // Product screens
        composable(
            route = Screen.Category.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            CategoryScreen(
                categoryId = categoryId,
                navController = navController
            )
        }

        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                navController = navController
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }

        // Cart & Checkout
        composable(Screen.Cart.route) {
            CartScreen(navController = navController)
        }

        composable(Screen.Checkout.route) {
            CheckoutScreen(navController = navController)
        }

        composable(
            route = Screen.OrderConfirmation.route,
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            OrderConfirmationScreen(
                orderId = orderId,
                navController = navController
            )
        }

        // Orders
        composable(Screen.Orders.route) {
            OrdersScreen(navController = navController)
        }

        composable(
            route = Screen.OrderDetail.route,
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            OrderDetailScreen(
                orderId = orderId,
                navController = navController
            )
        }

        // Profile
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        // TODO: Add admin screens when implemented
        // - AdminDashboard
        // - AdminOrders
        // - AdminProducts
        // - AdminProductForm
    }
}
