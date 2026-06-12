package com.epicActivities.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.epicActivities.presentation.detail.DetailScreen
import com.epicActivities.presentation.home.HomeScreen

@Composable
fun NavigationRoot() {
    val backStack = rememberNavBackStack(Route.Home)

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Route.Home> {
                HomeScreen(
                    onNavigateToDetail = {
                        backStack.addIfAbsent(Route.Detail)
                    }
                )
            }
            entry<Route.Detail> {
                DetailScreen(
                    onBack = {
                        backStack.popIfNotRoot()
                    }
                )
            }
        },
    )
}
