package com.mk.visitfence.ui.visits

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun VisitListScreen(
    viewmodel : VisitListViewModel
){

    val visits by viewmodel.visitFenceStateFlow.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(visits) { visit ->
            VisitItem(visit)
        }
    }

}

@Composable
fun VisitItem(
    visit: VisitUiModel
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = visit.locationName,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            Text("Date: ${visit.date}")

            Text(
                text = "Entry: ${visit.entryTime}",
                color = Color.Gray
            )

            Text(
                text = "Exit: ${
                    visit.exitTime
                }",
                color = Color.Gray
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Duration: ${
                    visit.duration?.toReadableDuration() ?: "In progress"
                }",
                fontWeight = FontWeight.Medium
            )
        }
    }
}


private fun Long.toReadableDuration(): String {
    val totalSeconds = this / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return when {
        hours > 0 ->
            String.format("%dh %02dm", hours, minutes)
        minutes > 0 ->
            String.format("%dm %02ds", minutes, seconds)
        else ->
            String.format("%ds", seconds)
    }
}