package com.epicActivities.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.epicActivities.presentation.gpx.upload.GpxUploadScreen
import com.epicActivities.presentation.home.HomeScreen
import com.epicActivities.presentation.photo.selection.PhotoSelectionScreen
import com.epicActivities.presentation.strava.activities.StravaActivitiesScreen

@Composable
fun NavigationRoot() {
    val backStack = rememberNavBackStack(Route.Home)

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Route.Home> {
                HomeScreen(
                    onNavigateToStravaActivities = {
                        backStack.addIfAbsent(Route.StravaActivities)
                    },
                    onNavigateToGpxUpload = {
                        backStack.addIfAbsent(Route.GpxUpload)
                    },
                )
            }
            entry<Route.StravaActivities> {
                StravaActivitiesScreen(
                    onBack = { backStack.popIfNotRoot() },
                    onActivitySelected = { activity ->
                        backStack.add(Route.PhotoSelection(activity.id))
                    },
                )
            }
            entry<Route.GpxUpload> {
                GpxUploadScreen(
                    onBack = { backStack.popIfNotRoot() },
                )
            }
            entry<Route.PhotoSelection> { route ->
                PhotoSelectionScreen(
                    activityId = route.activityId,
                    onBack = { backStack.popIfNotRoot() },
                )
            }
        },
    )
}
