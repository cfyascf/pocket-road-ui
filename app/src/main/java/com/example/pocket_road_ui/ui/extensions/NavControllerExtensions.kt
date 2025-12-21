package com.example.pocket_road_ui.ui.extensions

import androidx.navigation.NavController

fun NavController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        // Evita cópias na pilha
        launchSingleTop = true
        // Restaura o estado (scroll, inputs) se você já esteve nessa aba antes
        restoreState = true
        // Limpa a pilha para evitar que o botão "Voltar" fique passeando entre abas
        popUpTo(this@navigateSingleTopTo.graph.startDestinationId) {
            saveState = true
        }
    }
}