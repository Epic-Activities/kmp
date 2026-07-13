package com.epicActivities.presentation.strava.activities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.epicActivities.domain.model.Activity
import com.epicActivities.domain.model.SportType
import com.epicActivities.presentation.common.compose.PolylineCanvas
import kotlin.math.roundToInt

@Composable
fun ActivityItem(
    activity: Activity,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface,
        ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PolylineCanvas(
                polyline = activity.polyline,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SportTypeBadge(label = activity.sportType.displayName)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = activity.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = activity.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatText(label = "Distancia", value = formatDistance(activity.distanceKm))
                    StatText(label = "Tiempo", value = formatDuration(activity.durationSeconds))
                    if (activity.sportType != SportType.Swim) {
                        StatText(label = "Ritmo", value = formatPace(activity.distanceKm, activity.durationSeconds, activity.sportType))
                    }
                }
            }
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onClick() },
            )
        }
    }
}

@Composable
private fun StatText(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun SportTypeBadge(label: String) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}

private fun formatDistance(km: Double): String =
    if (km >= 10) "%.0f km".format(km) else "%.1f km".format(km)

private fun formatDuration(seconds: Int): String {
    val h = seconds / 3600
    val m = (seconds % 3600) / 60
    val s = seconds % 60
    return if (h > 0) "%d:%02d:%02d".format(h, m, s) else "%d:%02d".format(m, s)
}

private fun formatPace(km: Double, seconds: Int, sportType: SportType): String {
    if (km <= 0) return "--"
    return if (sportType == SportType.Ride) {
        val kmh = km / (seconds / 3600.0)
        "%.1f km/h".format(kmh)
    } else {
        val secPerKm = (seconds / km).roundToInt()
        val m = secPerKm / 60
        val s = secPerKm % 60
        "%d'%02d\"/km".format(m, s)
    }
}
