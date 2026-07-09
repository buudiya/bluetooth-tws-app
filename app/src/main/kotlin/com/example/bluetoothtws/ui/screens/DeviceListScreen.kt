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
fun DeviceListScreen(\n    devices: List<TWBSDevice>,\n    onDeviceSelected: (TWBSDevice) -> Unit,\n    onRefresh: () -> Unit,\n    isScanning: Boolean,\n    isLoading: Boolean = false\n) {\n    Column(\n        modifier = Modifier\n            .fillMaxSize()\n            .padding(top = 8.dp)\n    ) {\n        // Scan button header\n        Column(\n            modifier = Modifier\n                .fillMaxWidth()\n                .padding(16.dp),\n            horizontalAlignment = Alignment.CenterHorizontally\n        ) {\n            Button(\n                onClick = onRefresh,\n                enabled = !isScanning && !isLoading,\n                modifier = Modifier.fillMaxWidth(),\n                colors = ButtonDefaults.buttonColors(\n                    containerColor = MaterialTheme.colorScheme.primary,\n                    disabledContainerColor = MaterialTheme.colorScheme.outlineVariant\n                )\n            ) {\n                Icon(\n                    imageVector = Icons.Default.Search,\n                    contentDescription = \"Scan\",\n                    modifier = Modifier.padding(end = 8.dp)\n                )\n                Text(\n                    text = if (isScanning) \"Scanning...\" else \"Scan for Devices\",\n                    fontSize = 16.sp,\n                    fontWeight = FontWeight.SemiBold\n                )\n            }\n\n            // Info text\n            Text(\n                text = \"${devices.size} device(s) available\",\n                fontSize = 12.sp,\n                color = MaterialTheme.colorScheme.onSurfaceVariant,\n                modifier = Modifier.padding(top = 8.dp)\n            )\n        }\n\n        // Content\n        when {\n            isLoading -> LoadingState(\"Loading devices...\")\n            isScanning && devices.isEmpty() -> ScanningIndicator()\n            devices.isEmpty() -> EmptyDevicesState(isScanning = isScanning)\n            else -> {\n                LazyColumn(\n                    modifier = Modifier.fillMaxSize(),\n                    verticalArrangement = Arrangement.spacedBy(4.dp),\n                    contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 16.dp)\n                ) {\n                    items(\n                        items = devices,\n                        key = { it.address }\n                    ) { device ->\n                        DeviceCard(\n                            device = device,\n                            onClick = onDeviceSelected\n                        )\n                    }\n                }\n            }\n        }\n    }\n}\n