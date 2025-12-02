package com.tamarind.drinks.data.remote

import com.tamarind.drinks.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface TamarindApiService {

    // Authentication
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>

    @POST("auth/reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<MessageResponse>

    // Products
    @GET("products")
    suspend fun getProducts(
        @Query("category") category: String? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("sort") sort: String? = null
    ): Response<ProductsResponse>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") productId: String): Response<ProductDto>

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): Response<ProductsResponse>

    @GET("categories")
    suspend fun getCategories(): Response<CategoriesResponse>

    // Cart
    @GET("cart")
    suspend fun getCart(): Response<CartResponse>

    @POST("cart/items")
    suspend fun addToCart(@Body request: AddToCartRequest): Response<CartResponse>

    @PUT("cart/items/{id}")
    suspend fun updateCartItem(
        @Path("id") itemId: String,
        @Body request: UpdateCartItemRequest
    ): Response<CartResponse>

    @DELETE("cart/items/{id}")
    suspend fun removeFromCart(@Path("id") itemId: String): Response<CartResponse>

    @POST("cart/apply-coupon")
    suspend fun applyCoupon(@Body request: ApplyCouponRequest): Response<CartResponse>

    // Checkout & Orders
    @POST("checkout")
    suspend fun checkout(@Body request: CheckoutRequest): Response<CheckoutResponse>

    @GET("orders")
    suspend fun getOrders(): Response<OrdersResponse>

    @GET("orders/{id}")
    suspend fun getOrderById(@Path("id") orderId: String): Response<OrderDto>

    // Payment
    @POST("payment/intent")
    suspend fun createPaymentIntent(@Body request: PaymentIntentRequest): Response<PaymentIntentResponse>

    @POST("payment/mpesa/stk")
    suspend fun initiateMpesaPayment(@Body request: MpesaPaymentRequest): Response<MpesaPaymentResponse>

    @GET("payment/mpesa/status/{transactionId}")
    suspend fun getMpesaPaymentStatus(@Path("transactionId") transactionId: String): Response<PaymentStatusResponse>

    // Admin
    @GET("admin/orders")
    suspend fun getAdminOrders(
        @Query("status") status: String? = null,
        @Query("page") page: Int = 1
    ): Response<OrdersResponse>

    @PUT("admin/orders/{id}/status")
    suspend fun updateOrderStatus(
        @Path("id") orderId: String,
        @Body request: UpdateOrderStatusRequest
    ): Response<OrderDto>

    @POST("admin/products")
    suspend fun createProduct(@Body request: ProductDto): Response<ProductDto>

    @PUT("admin/products/{id}")
    suspend fun updateProduct(
        @Path("id") productId: String,
        @Body request: ProductDto
    ): Response<ProductDto>

    @DELETE("admin/products/{id}")
    suspend fun deleteProduct(@Path("id") productId: String): Response<MessageResponse>

    // User Profile
    @GET("profile")
    suspend fun getProfile(): Response<UserDto>

    @PUT("profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): Response<UserDto>

    @GET("addresses")
    suspend fun getAddresses(): Response<List<AddressDto>>

    @POST("addresses")
    suspend fun addAddress(@Body request: AddressDto): Response<AddressDto>

    @PUT("addresses/{id}")
    suspend fun updateAddress(
        @Path("id") addressId: String,
        @Body request: AddressDto
    ): Response<AddressDto>

    @DELETE("addresses/{id}")
    suspend fun deleteAddress(@Path("id") addressId: String): Response<MessageResponse>
}
