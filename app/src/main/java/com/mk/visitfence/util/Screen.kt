package com.mk.visitfence.util

sealed class Screen(val route : String , val title : String) {
    object Map : Screen("map","Map")
    object Geofences : Screen("geofences","Geofences")
    object Visits : Screen("visits","Visits")
}