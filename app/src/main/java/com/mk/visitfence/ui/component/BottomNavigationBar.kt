package com.mk.visitfence.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mk.visitfence.util.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {

    val items = listOf(
        Screen.Map, Screen.Geofences,Screen.Visits
    )

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                selected = (currentRoute == screen.route),
                onClick = {
                    navController.navigate(screen.route){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = when(screen){
                            Screen.Map -> Icons.Default.Home
                            Screen.Geofences -> Icons.Default.Place
                            Screen.Visits -> Icons.Default.Info
                        },
                        contentDescription = screen.title
                    )
                },
                label = {
                    Text(screen.title)
                }
            )
        }
    }
}