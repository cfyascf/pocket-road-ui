package com.example.pocket_road_ui.ui.navigation

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pocket_road_ui.ui.extensions.navigateSingleTopTo
import com.example.pocket_road_ui.ui.screens.capture.CaptureScreen
import com.example.pocket_road_ui.ui.screens.cardetails.CarDetailScreen
import com.example.pocket_road_ui.ui.screens.cardex.CardexScreen
import com.example.pocket_road_ui.ui.screens.friendprofile.mockCars
import com.example.pocket_road_ui.ui.screens.profile.ProfileScreen

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Cardex : Screen("cardex_screen")
    object CarDetail : Screen("car_detail_screen/{carId}") {
        const val ARG_CAR_ID = "carId"
        fun createRoute(carId: String) = "car_detail_screen/$carId"
    }
    object Capture : Screen("capture_screen")
    object Profile : Screen("profile_screen")
}

@Composable
fun AppNavigation(navController: NavHostController) {

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

                // cleans the stack to avoid getting back on back button clicked
                onNavigateToLoginScreen = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },

                onNavigateToCardexScreen = { navController.navigateSingleTopTo(Screen.Cardex.route) },
                onNavigateToCaptureScreen = { navController.navigateSingleTopTo(Screen.Capture.route) },
                onNavigateToProfileScreen = { navController.navigateSingleTopTo(Screen.Profile.route) }
            )
        }

        composable(
            route = Screen.CarDetail.route,
            arguments = listOf(
                navArgument(Screen.CarDetail.ARG_CAR_ID) { type = NavType.StringType }
            )
        ) { backStackEntry ->

            CarDetailScreen(
                onClose = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Capture.route) {
            CaptureScreen(
                onNavigateToCardex = { navController.navigateSingleTopTo(Screen.Cardex.route) }
            )
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(
                // cleans the stack to avoid getting back on back button clicked
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onFriendClick = {},

                onNavigateToCardexScreen = { navController.navigateSingleTopTo(Screen.Cardex.route) },
                onNavigateToCaptureScreen = { navController.navigateSingleTopTo(Screen.Capture.route) },
                onNavigateToProfileScreen = { navController.navigateSingleTopTo(Screen.Profile.route) }
            )
        }
    }
}