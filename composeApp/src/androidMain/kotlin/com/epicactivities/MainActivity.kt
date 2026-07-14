package com.epicActivities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.epicActivities.platform.initAppContext
import com.epicActivities.presentation.common.StravaCodeHolder
import com.epicActivities.presentation.common.navigation.NavigationRoot
import com.epicActivities.presentation.common.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppContext(this)
        enableEdgeToEdge()
        handleDeepLink(intent)
        setContent {
            AppTheme {
                NavigationRoot()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        val uri: Uri = intent?.data ?: return
        if (uri.scheme == "epicactivities" && uri.host == "strava-callback") {
            val code = uri.getQueryParameter("code") ?: return
            StravaCodeHolder.set(code)
        }
    }
}
