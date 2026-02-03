package com.mk.visitfence.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGeofenceDialog(
    latLng: LatLng,
    onDismiss: () ->  Unit,
    onConfirm: (String, Float) -> Unit
){

    var name by remember { mutableStateOf("") }
    var radius by remember { mutableFloatStateOf(30f) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(name, radius)
                                   onDismiss()
            }) {
                Text("Add fence")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss  ) {
                Text("Cancel")
            }
        },
        title = { Text("Add Geofence") },
        text = {
            Column {
                Text("Latitude: ${latLng.latitude}")
                Text("Longitude: ${latLng.longitude}")

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = {name = it},
                    label = { Text("Location name") },
                    singleLine = true
                )

                Spacer(Modifier.height(8.dp))

                Text("Radius: ${radius.toInt()} meters")

                Slider(value = radius , onValueChange = {radius = it} , valueRange = 10f..50f , steps = 3)
            }
        }
    )

}