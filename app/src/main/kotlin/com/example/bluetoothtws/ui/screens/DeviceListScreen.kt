package com.example.bluetoothtws.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.bluetoothtws.ui.components.DeviceCard
import com.example.bluetoothtws.ui.components.EmptyDevicesState
import com.example.bluetoothtws.ui.components.LoadingState
import com.example.bluetoothtws.ui.components.ScanningIndicator

@Composable
fun DeviceListScreen(
    devices: List<TWBSDevice>,
    onDeviceSelected: (TWBSDevice) -> Unit,
    onRefresh: () -> Unit,
    isScanning: Boolean,
    isLoading: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        // Scan button header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onRefresh,
                enabled = !isScanning && !isLoading,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.outlineVariant
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Scan",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = if (isScanning) "Scanning..." else "Scan for Devices",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Info text
            Text(
                text = "${devices.size} device(s) available",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Content
        when {
            isLoading -> LoadingState("Loading devices...")
            isScanning && devices.isEmpty() -> ScanningIndicator()
            devices.isEmpty() -> EmptyDevicesState(isScanning = isScanning)
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 16.dp)
                ) {
                    items(
                        items = devices,
                        key = { it.address }
                    ) { device ->
                        DeviceCard(
                            device = device,
                            onClick = onDeviceSelected
                        )
                    }
                }
            }
        }
    }
}
