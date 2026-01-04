package com.example.pocket_road_ui.ui.extensions

import androidx.navigation.NavController

fun NavController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        // avoids copies in queue
        launchSingleTop = true
        // restore the ui state if the user has already been there
        restoreState = true
        // cleans the stack to avoid getting back on back button clicked
        popUpTo(this@navigateSingleTopTo.graph.startDestinationId) {
            saveState = true
        }
    }
}