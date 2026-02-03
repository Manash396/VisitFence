package com.mk.visitfence.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationProvider(context : Context) {

    private val client  = LocationServices.getFusedLocationProviderClient(context)

    // callback hell problem is solved
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation() : LatLng? = suspendCancellableCoroutine { cont ->
        client.lastLocation
            .addOnSuccessListener { location ->
                if (cont.isActive){
                    cont.resume(
                        location?.let {
                            LatLng(it.latitude, it.longitude)
                        }
                    )
//                    Log.d("Krishnaradhe",location)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Krishnaradhe",exception.message.toString())
                if (cont.isActive) {
                    cont.resume(null)
                }
            }

        cont.invokeOnCancellation {
            // Optional cleanup (nothing needed for FusedLocationProvider)
        }

    }
}