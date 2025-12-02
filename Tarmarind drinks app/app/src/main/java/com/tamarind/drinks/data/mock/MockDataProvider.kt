package com.tamarind.drinks.data.mock

import com.tamarind.drinks.data.local.entity.*
import java.util.*

object MockDataProvider {
    
    fun getMockProducts(): List<ProductEntity> {
        return listOf(
            // Tamarind Juice Category
            ProductEntity(
                id = "prod-001",
                name = "Classic Tamarind Juice",
                description = "Refreshing tamarind juice made from premium quality tamarind pulp. Perfect balance of sweet and tangy flavors. Rich in vitamins and antioxidants.",
                price = 4.99,
                category = "juice",
                imageUrl = "https://images.unsplash.com/photo-1600271886742-f049cd451bba?w=400",
                stock = 50,
                rating = 4.5,
                reviewCount = 128,
                isAvailable = true
            ),
            ProductEntity(
                id = "prod-002",
                name = "Spiced Tamarind Delight",
                description = "Our signature blend with a hint of exotic spices. A unique twist on traditional tamarind drink with cardamom and ginger notes.",
                price = 5.49,
                category = "juice",
                imageUrl = "https://images.unsplash.com/photo-1546173159-315724a31696?w=400",
                stock = 35,
                rating = 4.7,
                reviewCount = 95,
                isAvailable = true
            ),
            ProductEntity(
                id = "prod-003",
                name = "Mango Tamarind Fusion",
                description = "Tropical mango meets tangy tamarind in this exotic blend. Naturally sweetened with real mango pulp.",
                price = 5.99,
                category = "juice",
                imageUrl = "https://images.unsplash.com/photo-1559181567-c3190ca9959b?w=400",
                stock = 42,
                rating = 4.8,
                reviewCount = 156,
                isAvailable = true
            ),
            
            // Concentrates Category
            ProductEntity(
                id = "prod-004",
                name = "Tamarind Concentrate 500ml",
                description = "Pure tamarind concentrate for making authentic drinks at home. Just add water and sweetener to taste. Makes 5 liters of juice.",
                price = 12.99,
                category = "concentrate",
                imageUrl = "https://images.unsplash.com/photo-1581636625402-29b2a704ef13?w=400",
                stock = 28,
                rating = 4.6,
                reviewCount = 73,
                isAvailable = true
            ),
            ProductEntity(
                id = "prod-005",
                name = "Premium Tamarind Extract",
                description = "Concentrated tamarind extract with no added preservatives. Perfect for cooking and beverages. Professional grade quality.",
                price = 18.99,
                category = "concentrate",
                imageUrl = "https://images.unsplash.com/photo-1587049352846-4a222e784337?w=400",
                stock = 15,
                rating = 4.9,
                reviewCount = 42,
                isAvailable = true
            ),
            
            // Syrups Category
            ProductEntity(
                id = "prod-006",
                name = "Tamarind Simple Syrup",
                description = "Ready-to-use tamarind syrup for cocktails, mocktails, and desserts. Mixologist's favorite for unique flavors.",
                price = 9.99,
                category = "syrup",
                imageUrl = "https://images.unsplash.com/photo-1514733670139-4d87a1941d55?w=400",
                stock = 60,
                rating = 4.4,
                reviewCount = 89,
                isAvailable = true
            ),
            ProductEntity(
                id = "prod-007",
                name = "Tamarind Honey Syrup",
                description = "Natural honey blended with tamarind for a perfect sweet and tangy combination. Great for teas and breakfast.",
                price = 11.99,
                category = "syrup",
                imageUrl = "https://images.unsplash.com/photo-1587049352851-8d4e89133924?w=400",
                stock = 45,
                rating = 4.7,
                reviewCount = 67,
                isAvailable = true
            ),
            
            // Combo Packs
            ProductEntity(
                id = "prod-008",
                name = "Tamarind Variety Pack",
                description = "Try all our favorites! Includes Classic Juice, Spiced Delight, and Mango Fusion (2 bottles each). Perfect for sharing.",
                price = 29.99,
                category = "combo",
                imageUrl = "https://images.unsplash.com/photo-1610873167013-2dd675d30ef4?w=400",
                stock = 20,
                rating = 4.9,
                reviewCount = 210,
                isAvailable = true
            ),
            ProductEntity(
                id = "prod-009",
                name = "DIY Tamarind Kit",
                description = "Everything you need to make tamarind drinks at home. Contains concentrate, syrup, recipe book, and mixing tools.",
                price = 39.99,
                category = "combo",
                imageUrl = "https://images.unsplash.com/photo-1556910096-6f5e72db6803?w=400",
                stock = 12,
                rating = 4.8,
                reviewCount = 45,
                isAvailable = true
            ),
            
            // More products
            ProductEntity(
                id = "prod-010",
                name = "Organic Tamarind Juice",
                description = "100% organic certified tamarind juice. No artificial colors or flavors. Sustainably sourced from certified farms.",
                price = 6.99,
                category = "juice",
                imageUrl = "https://images.unsplash.com/photo-1622597467836-f3285f2131b8?w=400",
                stock = 38,
                rating = 4.6,
                reviewCount = 92,
                isAvailable = true
            ),
            ProductEntity(
                id = "prod-011",
                name = "Sugar-Free Tamarind Drink",
                description = "All the flavor with zero sugar. Sweetened with natural stevia. Perfect for health-conscious consumers.",
                price = 5.49,
                category = "juice",
                imageUrl = "https://images.unsplash.com/photo-1523677011781-c91d1bbe2f9d?w=400",
                stock = 55,
                rating = 4.3,
                reviewCount = 78,
                isAvailable = true
            ),
            ProductEntity(
                id = "prod-012",
                name = "Tamarind Lemonade",
                description = "Refreshing fusion of tamarind and fresh lemon. Perfect summer cooler with a unique twist.",
                price = 4.99,
                category = "juice",
                imageUrl = "https://images.unsplash.com/photo-1523677011781-c91d1bbe2f9d?w=400",
                stock = 47,
                rating = 4.5,
                reviewCount = 102,
                isAvailable = true
            )
        )
    }
    
    fun getMockUser(): UserEntity {
        return UserEntity(
            uid = "user-001",
            email = "demo@tamarinddrinks.com",
            displayName = "Demo User",
            photoUrl = null,
            isAdmin = false,
            createdAt = System.currentTimeMillis()
        )
    }
    
    fun getMockAdminUser(): UserEntity {
        return UserEntity(
            uid = "admin-001",
            email = "admin@tamarinddrinks.com",
            displayName = "Admin User",
            photoUrl = null,
            isAdmin = true,
            createdAt = System.currentTimeMillis()
        )
    }
    
    fun getMockAddresses(userId: String): List<AddressEntity> {
        return listOf(
            AddressEntity(
                id = "addr-001",
                userId = userId,
                fullName = "John Doe",
                phoneNumber = "+254712345678",
                street = "123 Moi Avenue",
                city = "Nairobi",
                state = "Nairobi County",
                postalCode = "00100",
                country = "Kenya",
                isDefault = true
            ),
            AddressEntity(
                id = "addr-002",
                userId = userId,
                fullName = "John Doe",
                phoneNumber = "+254712345678",
                street = "456 Kenyatta Street",
                city = "Mombasa",
                state = "Mombasa County",
                postalCode = "80100",
                country = "Kenya",
                isDefault = false
            )
        )
    }
    
    fun getMockOrders(userId: String): List<OrderEntity> {
        val now = System.currentTimeMillis()
        val oneDay = 24 * 60 * 60 * 1000L
        
        return listOf(
            OrderEntity(
                id = "order-001",
                userId = userId,
                orderNumber = "TDR2024001",
                status = "delivered",
                items = listOf(
                    CartItemEntity("cart-001", userId, "prod-001", "Classic Tamarind Juice", 2, 4.99, "https://images.unsplash.com/photo-1600271886742-f049cd451bba?w=400"),
                    CartItemEntity("cart-002", userId, "prod-003", "Mango Tamarind Fusion", 1, 5.99, "https://images.unsplash.com/photo-1559181567-c3190ca9959b?w=400")
                ),
                subtotal = 15.97,
                tax = 1.60,
                deliveryFee = 3.00,
                discount = 0.0,
                total = 20.57,
                deliveryAddress = getMockAddresses(userId)[0],
                paymentMethod = "Card ending in 4242",
                createdAt = now - (7 * oneDay),
                updatedAt = now - (4 * oneDay)
            ),
            OrderEntity(
                id = "order-002",
                userId = userId,
                orderNumber = "TDR2024002",
                status = "processing",
                items = listOf(
                    CartItemEntity("cart-003", userId, "prod-008", "Tamarind Variety Pack", 1, 29.99, "https://images.unsplash.com/photo-1610873167013-2dd675d30ef4?w=400")
                ),
                subtotal = 29.99,
                tax = 3.00,
                deliveryFee = 5.00,
                discount = 5.00,
                total = 32.99,
                deliveryAddress = getMockAddresses(userId)[0],
                paymentMethod = "M-PESA",
                createdAt = now - (2 * oneDay),
                updatedAt = now - oneDay
            ),
            OrderEntity(
                id = "order-003",
                userId = userId,
                orderNumber = "TDR2024003",
                status = "pending",
                items = listOf(
                    CartItemEntity("cart-004", userId, "prod-010", "Organic Tamarind Juice", 3, 6.99, "https://images.unsplash.com/photo-1622597467836-f3285f2131b8?w=400"),
                    CartItemEntity("cart-005", userId, "prod-006", "Tamarind Simple Syrup", 2, 9.99, "https://images.unsplash.com/photo-1514733670139-4d87a1941d55?w=400")
                ),
                subtotal = 40.95,
                tax = 4.10,
                deliveryFee = 3.00,
                discount = 0.0,
                total = 48.05,
                deliveryAddress = getMockAddresses(userId)[1],
                paymentMethod = "Google Pay",
                createdAt = now - oneDay,
                updatedAt = now - oneDay
            )
        )
    }
    
    fun getMockCartItems(userId: String): List<CartItemEntity> {
        return listOf(
            CartItemEntity(
                id = "cart-current-001",
                userId = userId,
                productId = "prod-002",
                productName = "Spiced Tamarind Delight",
                quantity = 2,
                price = 5.49,
                imageUrl = "https://images.unsplash.com/photo-1546173159-315724a31696?w=400"
            ),
            CartItemEntity(
                id = "cart-current-002",
                userId = userId,
                productId = "prod-007",
                productName = "Tamarind Honey Syrup",
                quantity = 1,
                price = 11.99,
                imageUrl = "https://images.unsplash.com/photo-1587049352851-8d4e89133924?w=400"
            )
        )
    }
    
    // Helper to get featured products (for home screen)
    fun getFeaturedProducts(): List<ProductEntity> {
        return getMockProducts().filter { 
            it.id in listOf("prod-001", "prod-003", "prod-008", "prod-010", "prod-002", "prod-006")
        }
    }
    
    // Helper to get products by category
    fun getProductsByCategory(category: String): List<ProductEntity> {
        return getMockProducts().filter { it.category == category }
    }
    
    // Helper to search products
    fun searchProducts(query: String): List<ProductEntity> {
        val lowerQuery = query.lowercase()
        return getMockProducts().filter { 
            it.name.lowercase().contains(lowerQuery) || 
            it.description.lowercase().contains(lowerQuery)
        }
    }
}
