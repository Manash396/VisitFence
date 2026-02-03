package com.mk.visitfence.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.mk.visitfence.R

object NotificationHelper {

    fun sendNotificationForFence(context: Context,message : String){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            // for this version need to check if permission is given by user
            val permissionGranted =  (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED )

            if (!permissionGranted){
                return
            }

            val notification = NotificationCompat.Builder(context,"fence_alert")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Fence Alert")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

            NotificationManagerCompat.from(context).notify(1001,notification)

        }
    }

}