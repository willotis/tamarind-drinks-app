package com.tamarind.drinks.ui.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tamarind.drinks.data.local.entity.OrderEntity
import com.tamarind.drinks.ui.navigation.Screen
import com.tamarind.drinks.utils.Resource
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel()
) {
 val ordersState by viewModel.ordersState.collectAsState()
    val userId = "user-001"
    
    LaunchedEffect(Unit) {
        viewModel.loadOrders(userId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Orders") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Filter tabs
            ScrollableTabRow(
                selectedTabIndex = OrderFilter.values().indexOf(ordersState.selectedFilter),
                edgePadding = 16.dp
            ) {
                OrderFilter.values().forEach { filter ->
                    Tab(
                        selected = ordersState.selectedFilter == filter,
                        onClick = { viewModel.filterOrders(filter) },
                        text = { Text(filter.displayName) }
                    )
                }
            }
            
            when {
                ordersState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                ordersState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = ordersState.error ?: "Unknown error")
                    }
                }
                ordersState.filteredOrders.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Receipt,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No orders found",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }
                else -> {
                    LazyColumn(contentPadding = PaddingValues(16.dp)) {
                        items(ordersState.filteredOrders) { order ->
                            OrderCard(
                                order = order,
                                onClick = {
                                    navController.navigate(Screen.OrderDetail.createRoute(order.id))
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: OrderEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.orderNumber,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                OrderStatusBadge(order.status)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = formatDate(order.createdAt),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${order.items.size} item${if (order.items.size > 1) "s" else ""}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$${String.format("%.2f", order.total)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun OrderStatusBadge(status: String) {
    val (color, text) = when (status) {
        "pending" -> MaterialTheme.colorScheme.secondaryContainer to "Pending"
        "processing" -> MaterialTheme.colorScheme.tertiaryContainer to "Processing"
        "delivered" -> MaterialTheme.colorScheme.primaryContainer to "Delivered"
        "cancelled" -> MaterialTheme.colorScheme.errorContainer to "Cancelled"
        else -> MaterialTheme.colorScheme.surfaceVariant to status
    }
    
    Surface(
        color = color,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

// Order Detail Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    orderId: String,
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val orderDetailState by viewModel.orderDetailState.collectAsState()
    val userId = "user-001"
    
    LaunchedEffect(orderId) {
        viewModel.loadOrderDetail(orderId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when (val state = orderDetailState) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                val order = state.data
                if (order != null) {
                    LazyColumn(
                        modifier = Modifier.padding(paddingValues),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        item {
                            OrderDetailHeader(order)
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                        
                        item {
                            Text(
                                text = "Items",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = Font Weight.Bold
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        
                        items(order.items) { item ->
                            OrderItemCard(item)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            OrderSummarySection(order)
                        }
                        
                        if (order.status in listOf("pending", "processing")) {
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                                OutlinedButton(
                                    onClick = { viewModel.cancelOrder(orderId, userId) },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.Cancel, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Cancel Order")
                                }
                            }
                        }
                    }
                }
            }
            is Resource.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.message ?: "Error loading order")
                }
            }
            else -> {}
        }
    }
}

@Composable
fun OrderDetailHeader(order: OrderEntity) {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = order.orderNumber,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = formatDate(order.createdAt),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                OrderStatusBadge(order.status)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Delivery Address",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = order.deliveryAddress.fullName)
            Text(text = "${order.deliveryAddress.street}, ${order.deliveryAddress.city}")
            Text(text = order.deliveryAddress.phoneNumber)
            
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Payment Method",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = order.paymentMethod)
        }
    }
}

@Composable
fun OrderItemCard(item: com.tamarind.drinks.data.local.entity.CartItemEntity) {
    Card {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.productName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Qty: ${item.quantity}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "$${String.format("%.2f", item.price * item.quantity)}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun OrderSummarySection(order: OrderEntity) {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Order Summary",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            SummaryRow("Subtotal", order.subtotal)
            SummaryRow("Tax", order.tax)
            SummaryRow("Delivery", order.deliveryFee)
            if (order.discount > 0) {
                SummaryRow("Discount", -order.discount, true)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${String.format("%.2f", order.total)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun SummaryRow(label: String, amount: Double, isDiscount: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "$${String.format("%.2f", amount)}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = if (isDiscount) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}
