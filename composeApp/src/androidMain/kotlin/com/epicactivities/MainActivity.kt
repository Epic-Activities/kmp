package com.epicActivities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.epicActivities.platform.initAppContext
import com.epicActivities.presentation.common.navigation.NavigationRoot
import com.epicActivities.presentation.common.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppContext(this)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                NavigationRoot()
            }
        }
    }
}
