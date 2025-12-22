package com.example.pocket_road_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.pocket_road_ui.data.local.SessionManager
import com.example.pocket_road_ui.ui.navigation.AppNavigation
import com.example.pocket_road_ui.ui.navigation.Screen
import com.example.pocket_road_ui.ui.theme.AutoDexTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            // initialValue="LOADING" so you don't jump to login
            // before data store finishes reading from disk
            val tokenState by sessionManager.accessToken
                .collectAsStateWithLifecycle(initialValue = "LOADING")

            // global session monitor
            LaunchedEffect(tokenState) {
                if (tokenState == null) {
                    navController.navigate(Screen.Login.route) {
                        // cleans all navigation pile
                        popUpTo(0) { inclusive = true }
                    }
                }
            }

            AutoDexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (tokenState == "LOADING") {
                        // loading screen
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else {
                        AppNavigation(navController = navController)
                    }
                }
            }
        }
    }
}