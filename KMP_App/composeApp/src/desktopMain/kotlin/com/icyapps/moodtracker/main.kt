package com.icyapps.moodtracker

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.unit.dp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Mood Tracker",
        state = rememberWindowState(width = 400.dp, height = 600.dp),
        resizable = true
    ) {
        App()
    }
}