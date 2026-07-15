package com.epicActivities.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.epicActivities.presentation.home.HomeScreen
import com.epicActivities.presentation.photo.selection.PhotoSelectionScreen
import com.epicActivities.presentation.preview.PreviewScreen
import com.epicActivities.presentation.strava.activities.StravaActivitiesScreen
import com.epicActivities.presentation.strava.activities.StravaActivitiesViewModel

@Composable
fun NavigationRoot() {
    val backStack = rememberNavBackStack(Route.Home)
    val stravaVm: StravaActivitiesViewModel = viewModel()

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Route.Home> {
                HomeScreen(
                    onNavigateToStravaActivities = {
                        backStack.add(Route.StravaActivities)
                    },
                )
            }
            entry<Route.StravaActivities> {
                StravaActivitiesScreen(
                    viewModel = stravaVm,
                    onBack = { backStack.popIfNotRoot() },
                    onActivitiesSelected = { activities ->
                        backStack.add(Route.PhotoSelection(activities = activities))
                    },
                )
            }
            entry<Route.PhotoSelection> { route ->
                val first = route.activities.first()
                PhotoSelectionScreen(
                    activityTitle = first.title,
                    polyline = first.polyline,
                    onBack = { backStack.popIfNotRoot() },
                    onNavigateToPreview = { photoUri ->
                        backStack.add(
                            Route.Preview(
                                activities = route.activities,
                                photoUri = photoUri,
                            ),
                        )
                    },
                )
            }
            entry<Route.Preview> { route ->
                PreviewScreen(
                    activities = route.activities,
                    photoUri = route.photoUri,
                    onBack = { backStack.popIfNotRoot() },
                    onBackToActivities = {
                        backStack.popIfNotRoot() // saca Preview
                        backStack.popIfNotRoot() // saca PhotoSelection
                    },
                    onMakeAnother = {
                        stravaVm.clearSelection()
                        backStack.popIfNotRoot() // saca Preview
                        backStack.popIfNotRoot() // saca PhotoSelection
                    },
                )
            }
        },
    )
}
