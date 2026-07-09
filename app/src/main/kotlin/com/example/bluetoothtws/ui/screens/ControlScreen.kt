package com.example.bluetoothtws.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.example.bluetoothtws.ui.components.DeviceInfoPanel
import com.example.bluetoothtws.ui.components.PlaybackControlsSection
import com.example.bluetoothtws.ui.components.VolumeControlSection

@Composable
fun ControlScreen(
    device: TWBSDevice,
    isPlaying: Boolean = false,
    volumeLevel: Int = 50,
    connectionState: String = "Connected",
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onVolumeUp: () -> Unit,
    onVolumeDown: () -> Unit,
    onDisconnect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Device Info Panel
        DeviceInfoPanel(
            device = device,
            connectionState = connectionState
        )

        // Playback Controls
        PlaybackControlsSection(
            isPlaying = isPlaying,
            onPlayClick = onPlayClick,
            onPauseClick = onPauseClick,
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick
        )

        // Volume Control
        VolumeControlSection(
            volumeLevel = volumeLevel,
            onVolumeUp = onVolumeUp,
            onVolumeDown = onVolumeDown
        )

        // Disconnect Button
        Button(
            onClick = onDisconnect,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxSize(0.9f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Disconnect\",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Disconnect Device\",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
