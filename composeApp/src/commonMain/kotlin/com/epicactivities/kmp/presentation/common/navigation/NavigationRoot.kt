package com.epicactivities.kmp.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavDisplay
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.epicactivities.kmp.presentation.detail.DetailScreen
import com.epicactivities.kmp.presentation.home.HomeScreen

@Composable
fun NavigationRoot() {
    val backStack = rememberNavBackStack(Route.Home)

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Route.Home> {
                HomeScreen(
                    onNavigateToDetail = { backStack.add(Route.Detail) }
                )
            }
            entry<Route.Detail> {
                DetailScreen(
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        },
    )
}
