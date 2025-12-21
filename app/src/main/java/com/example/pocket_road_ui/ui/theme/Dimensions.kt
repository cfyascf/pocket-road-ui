package com.example.pocket_road_ui.ui.theme

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object AppDimensions {
    val navBarBottomPadding: Dp
        @Composable
        get() = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    val statusBarTopPadding: Dp
        @Composable
        get() = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val BottomBarHeight = 80.dp
}