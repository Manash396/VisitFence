package com.mk.visitfence.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mk.visitfence.data.repository.GeofenceRepository
import com.mk.visitfence.ui.component.BottomNavigationBar
import com.mk.visitfence.ui.geofenceList.GeofenceListScreen
import com.mk.visitfence.ui.geofenceList.GeofenceListViewModel
import com.mk.visitfence.ui.geofenceList.GeofenceListViewModelFactory
import com.mk.visitfence.ui.map.MapScreen
import com.mk.visitfence.ui.map.MapScreenViewModel
import com.mk.visitfence.ui.map.MapScreenViewModelFactory
import com.mk.visitfence.ui.visits.VisitListScreen
import com.mk.visitfence.ui.visits.VisitListViewModel
import com.mk.visitfence.ui.visits.VisitListViewModelFactory
import com.mk.visitfence.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(repo : GeofenceRepository){
    val navController  = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val title  = when(currentRoute){
        Screen.Geofences.route -> Screen.Geofences.title
        Screen.Visits.route -> Screen.Visits.title
        else -> "VisitFence"
    }

    val mapScreenViewModel : MapScreenViewModel = viewModel(
        factory = MapScreenViewModelFactory(repo)
    )

    val visitListViewModel : VisitListViewModel = viewModel(
        factory = VisitListViewModelFactory(repo)
    )

    val geofenceListViewModel : GeofenceListViewModel = viewModel(
        factory = GeofenceListViewModelFactory(repo)
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title)
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Map.route,
            modifier = Modifier.padding(paddingValues)
        ){
            composable(Screen.Map.route){
                MapScreen(mapScreenViewModel)
            }
            composable(Screen.Geofences.route){
                GeofenceListScreen(geofenceListViewModel)
            }
            composable(Screen.Visits.route){
                VisitListScreen(visitListViewModel)
            }
        }
    }
}