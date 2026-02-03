package com.mk.visitfence.ui.map


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.mk.visitfence.ui.component.AddGeofenceDialog
import com.mk.visitfence.util.LocationProvider


@Composable
fun MapScreen(
    viewModel: MapScreenViewModel
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val locationProvider = remember {
        LocationProvider(context.applicationContext)
    }

    var hasLocationPermission by remember {
        mutableStateOf(false)
    }
    var hasNotificationPermission by remember {
        mutableStateOf(false)
    }

    val permissionsLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            hasNotificationPermission = permissions[Manifest.permission.POST_NOTIFICATIONS] == true
        }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            hasNotificationPermission = true
            hasLocationPermission = true
            return@LaunchedEffect
        }

        val permissionsToRequest =  mutableListOf<String>()

        // location
        if ( ContextCompat.checkSelfPermission(
            context,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED){
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }else{
            hasLocationPermission = true
        }

        // notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if ( ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED){
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }else{
                hasNotificationPermission = true
            }
        }else{
            hasNotificationPermission = true
        }

        if (permissionsToRequest.isNotEmpty()) {
            permissionsLauncher.launch(permissionsToRequest.toTypedArray())
        }

    }

    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 17f)
    }

    // if i got the current position then update the camera position state
    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission){
            val latLng = locationProvider.getCurrentLocation()
            Log.d("Krishnaradhe",latLng.toString())
            latLng?.let {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it,17f)
            }
        }
    }


    var showDialog by remember { mutableStateOf(false) }
    var slatLng by remember { mutableStateOf<LatLng?>(null) }

    // collecting from flow to show ui update automatically
//    val geoFencesState  = viewModel.geofencesStateFlow.collectAsState() // state
    val geoFencesState by viewModel.geofencesStateFlow.collectAsState() // by == getValue()

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = hasLocationPermission
        ),
        onMapLongClick = { latLng ->
            slatLng = latLng
            showDialog = true
        }
    ) {

        geoFencesState.forEach { fence ->
            Marker(
                state = MarkerState(LatLng(fence.latitude, fence.longitude)),
                title = fence.name,
                snippet = "30 km fence"
            )
            Circle(
                center = LatLng(fence.latitude, fence.longitude),
                radius = 100.0,
                strokeColor = Color.Blue,
                fillColor = Color.Transparent
            )
        }
    }

    if (showDialog && slatLng != null) {
        AddGeofenceDialog(
            latLng = slatLng!!,
            onDismiss = {showDialog = false},
            onConfirm = { name, radius ->
                viewModel.addGeofence(name, slatLng!!)
            }
        )
    }
}