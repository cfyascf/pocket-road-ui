package com.example.pocket_road_ui.ui.navigation

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pocket_road_ui.ui.screens.cardetails.CarDetailScreen
import com.example.pocket_road_ui.ui.screens.home.HomeScreen
import com.example.pocket_road_ui.ui.screens.home.mockCars

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Home : Screen("home_screen")
    object CarDetail : Screen("car_detail_screen/{carId}") {
        const val ARG_CAR_ID = "carId"

        fun createRoute(carId: Int) = "car_detail_screen/$carId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            LoginScreen(onNavigateToHomeScreen = {
                navController.navigate(Screen.Home.route) {

                    // removes login screen from pile (user cannot come back to it)
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }

        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToCarDetail = { carId ->
                    navController.navigate(Screen.CarDetail.createRoute(carId))
                }
            )
        }

        composable(
            route = Screen.CarDetail.route,
            arguments = listOf(
                navArgument(Screen.CarDetail.ARG_CAR_ID) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val carId = backStackEntry.arguments?.getInt(Screen.CarDetail.ARG_CAR_ID)
            val car = mockCars.find { it.id == carId }

            if (car != null) {
                CarDetailScreen(
                    car = car,
                    onClose = { navController.popBackStack() }
                )
            }
        }
    }
}