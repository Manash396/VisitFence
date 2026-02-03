package com.mk.visitfence

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.mk.visitfence.data.local.DatabaseProvider
import com.mk.visitfence.data.repository.GeofenceRepository
import com.mk.visitfence.geofence.GeofenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        // App start sync
        CoroutineScope(Dispatchers.IO).launch {
            val db = DatabaseProvider.provideDatabase(this@MyApp)
            val manager = GeofenceManager(this@MyApp)
            val repo = GeofenceRepository(db.geofenceDao(), db.visitfenceDao(), manager)

            repo.syncGeofenceFromDB()
        }
    }

    private fun createNotificationChannel() {
        val name  = "Visit Fence Reminder"
        val descriptionText = "Reminds the visit"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("fence_alert", name, importance)
        channel.description = descriptionText

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}