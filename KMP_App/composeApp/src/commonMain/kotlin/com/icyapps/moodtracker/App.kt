package com.icyapps.moodtracker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.icyapps.moodtracker.di.appModule
import com.icyapps.moodtracker.presentation.screen.MoodTrackerScreen
import com.icyapps.moodtracker.presentation.viewmodel.MoodViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.mp.KoinPlatformTools

@Composable
@Preview
fun App() {
    var koinInitialized by remember { mutableStateOf(false) }
    
    // Initialize Koin
    LaunchedEffect(Unit) {
        startKoin {
            modules(appModule)
        }
        koinInitialized = true
    }
    
    // Cleanup Koin when the app is destroyed
    DisposableEffect(Unit) {
        onDispose {
            stopKoin()
        }
    }
    
    MaterialTheme {
        if (koinInitialized) {
            val viewModel: MoodViewModel = remember { 
                KoinPlatformTools.defaultContext().get().get() 
            }
            MoodTrackerScreen(viewModel = viewModel)
        } else {
            // Show loading while Koin initializes
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}