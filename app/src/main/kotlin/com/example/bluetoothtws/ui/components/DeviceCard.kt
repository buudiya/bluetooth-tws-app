package com.example.bluetoothtws.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.BluetoothConnected
import androidx.compose.material.icons.filled.Battery80
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetoothtws.models.TWBSDevice

@Composable
fun DeviceCard(
    device: TWBSDevice,
    onClick: (TWBSDevice) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(device) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (device.isConnected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header with name and status
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = if (device.isConnected) 
                            Icons.Default.BluetoothConnected 
                        else 
                            Icons.Default.Bluetooth,
                        contentDescription = "Bluetooth status",
                        tint = if (device.isConnected) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Column {
                        Text(
                            text = device.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = device.address,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Status badge
                StatusBadge(isConnected = device.isConnected)
            }

            // Battery and signal info
            if (device.isBatteryAvailable || device.rssi != 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (device.isBatteryAvailable) {
                        BatteryIndicator(
                            batteryLevel = device.batteryLevel ?: 0,
                            label = "Overall",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (device.batteryLevelLeft != null && device.batteryLevelRight != null) {
                        BatteryIndicator(
                            batteryLevel = device.batteryLevelLeft!!,
                            label = "Left",
                            modifier = Modifier.weight(1f)
                        )
                        BatteryIndicator(
                            batteryLevel = device.batteryLevelRight!!,
                            label = "Right",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // RSSI Signal strength
            if (device.rssi != 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Signal Strength:",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    SignalStrengthBar(rssi = device.rssi)
                }
            }
        }
    }
}

@Composable
fun StatusBadge(
    isConnected: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isConnected) 
        MaterialTheme.colorScheme.primary 
    else 
        MaterialTheme.colorScheme.outlineVariant
    
    val text = if (isConnected) "Connected" else "Available"

    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = if (isConnected) 
            MaterialTheme.colorScheme.onPrimary 
        else 
            MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}

@Composable
fun BatteryIndicator(
    batteryLevel: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = if (batteryLevel > 50) 
                    Icons.Default.BatteryFull 
                else 
                    Icons.Default.Battery80,
                contentDescription = "Battery",
                tint = when {
                    batteryLevel > 50 -> MaterialTheme.colorScheme.primary
                    batteryLevel > 20 -> MaterialTheme.colorScheme.tertiary
                    else -> MaterialTheme.colorScheme.error
                },
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                text = "$batteryLevel%",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Text(
            text = label,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SignalStrengthBar(
    rssi: Int,
    modifier: Modifier = Modifier
) {
    val signalStrength = when {
        rssi > -50 -> "Excellent"
        rssi > -60 -> "Good"
        rssi > -70 -> "Fair"
        else -> "Poor"
    }

    val signalColor = when {
        rssi > -50 -> MaterialTheme.colorScheme.primary
        rssi > -60 -> MaterialTheme.colorScheme.tertiary
        rssi > -70 -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.error
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = signalStrength,
            fontSize = 12.sp,
            color = signalColor,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "($rssi dBm)",
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
