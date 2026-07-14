package com.epicActivities.presentation.strava.activities

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epicActivities.domain.model.Activity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StravaActivitiesScreen(
    onBack: () -> Unit,
    onActivitiesSelected: (List<Activity>) -> Unit,
    viewModel: StravaActivitiesViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()
    val selectedCount = state.selectedIds.size
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler { showExitDialog = true }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("¿Salir de tu cuenta?") },
            text = { Text("Si sales tendrás que volver a conectar con Strava y cargar tus actividades.") },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    viewModel.disconnect()
                    onBack()
                }) {
                    Text("Salir")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("Quedarme")
                }
            },
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (selectedCount > 0) {
                        Text("$selectedCount seleccionada${if (selectedCount > 1) "s" else ""}")
                    } else {
                        Text("Seleccionar actividades")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { showExitDialog = true }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            if (selectedCount > 0) {
                ExtendedFloatingActionButton(
                    onClick = { onActivitiesSelected(state.selectedActivities) },
                    text = { Text(text = "Go") },
                    icon = {},
                )
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.error != null -> Text(
                    text = state.error!!,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                )
                state.activities.isEmpty() -> Text(
                    text = "No hay actividades disponibles.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(state.activities, key = { it.id }) { activity ->
                        ActivityItem(
                            activity = activity,
                            isSelected = activity.id in state.selectedIds,
                            onClick = { viewModel.toggleSelection(activity.id) },
                        )
                    }
                }
            }
        }
    }
}
