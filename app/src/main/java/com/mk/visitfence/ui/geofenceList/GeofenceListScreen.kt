package com.mk.visitfence.ui.geofenceList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GeofenceListScreen(
    viewModel: GeofenceListViewModel
) {
    val geofences by viewModel.geofencesStateFlow.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(geofences) { fence ->
            GeofenceItem(fence)
        }
    }
}

@Composable
fun GeofenceItem(
    geofence: GeofenceUiModel
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = geofence.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Lat: ${"%.5f".format(geofence.latitude)}"
            )
            Text(
                text = "Lng: ${"%.5f".format(geofence.longitude)}"
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Radius: ${geofence.radius}"
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Created: ${geofence.createdAt}",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
