package com.mk.visitfence.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mk.visitfence.ui.component.BottomNavigationBar
import com.mk.visitfence.ui.map.MapScreen
import com.mk.visitfence.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    val navController  = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "VisitFence")
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
                MapScreen()
            }
            composable(Screen.Geofences.route){

            }
            composable(Screen.Visits.route){

            }
        }
    }
}