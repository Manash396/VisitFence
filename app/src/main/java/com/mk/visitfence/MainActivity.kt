package com.mk.visitfence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mk.visitfence.data.local.DatabaseProvider
import com.mk.visitfence.data.repository.GeofenceRepository
import com.mk.visitfence.geofence.GeofenceManager
import com.mk.visitfence.ui.MainScreen
import com.mk.visitfence.ui.theme.VisitFenceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = DatabaseProvider.provideDatabase(applicationContext)
        val gm = GeofenceManager(this)
        val repo = GeofenceRepository(db.geofenceDao(), db.visitfenceDao(), gm)
        setContent {
            VisitFenceTheme {
                MainScreen(repo)
            }
        }
    }
}
