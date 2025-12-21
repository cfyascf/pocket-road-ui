package com.example.pocket_road_ui.ui.navigation

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pocket_road_ui.ui.extensions.navigateSingleTopTo
import com.example.pocket_road_ui.ui.screens.cardetails.CarDetailScreen
import com.example.pocket_road_ui.ui.screens.cardex.CardexScreen
import com.example.pocket_road_ui.ui.screens.cardex.mockCars
import com.example.pocket_road_ui.ui.screens.profile.ProfileScreen

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Cardex : Screen("cardex_screen")
    object CarDetail : Screen("car_detail_screen/{carId}") {
        const val ARG_CAR_ID = "carId"

        fun createRoute(carId: Int) = "car_detail_screen/$carId"
    }

    object Profile : Screen("profile_screen")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            LoginScreen(onNavigateToCardexScreen = {
                navController.navigate(Screen.Cardex.route) {

                    // removes login screen from pile (user cannot come back to it)
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }

        composable(route = Screen.Cardex.route) {
            CardexScreen(
                onNavigateToCarDetail = { carId ->
                    navController.navigate(Screen.CarDetail.createRoute(carId))
                },
                onNavigateToCardexScreen = { navController.navigateSingleTopTo(Screen.Cardex.route) },
                onNavigateToCaptureScreen = { navController.navigate(Screen.Cardex.route) },
                onNavigateToProfileScreen = { navController.navigate(Screen.Profile.route) }
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

        composable(route = Screen.Profile.route) {
            ProfileScreen(
                onLogout = {},
                onFriendClick = {},
                onNavigateToCardexScreen = { navController.navigate(Screen.Cardex.route) },
                onNavigateToCaptureScreen = { navController.navigate(Screen.Cardex.route) },
                onNavigateToProfileScreen = { navController.navigateSingleTopTo(Screen.Cardex.route) }
            )
        }
    }
}