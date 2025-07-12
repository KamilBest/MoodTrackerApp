package com.icyapps.moodtracker.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icyapps.moodtracker.presentation.viewmodel.MoodUiState
import com.icyapps.moodtracker.presentation.viewmodel.MoodViewModel

@Composable
fun MoodTrackerScreen(
    viewModel: MoodViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            text = "Mood Tracker",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Error/Success Messages
        uiState.error?.let { error ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    IconButton(onClick = { viewModel.clearError() }) {
                        Text("×", fontSize = 20.sp)
                    }
                }
            }
        }

        uiState.successMessage?.let { message ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    IconButton(onClick = { viewModel.clearSuccessMessage() }) {
                        Text("×", fontSize = 20.sp)
                    }
                }
            }
        }

        if (uiState.showHistory) {
            // History Section
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Mood History",
                            fontWeight = FontWeight.Medium
                        )
                        Button(
                            onClick = { viewModel.hideHistory() }
                        ) {
                            Text("Close")
                        }
                    }
                    
                    if (uiState.isLoadingHistory) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else if (uiState.moodHistory.isEmpty()) {
                        Text(
                            text = "No mood entries yet",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.moodHistory.reversed()) { entry ->
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = entry.mood.label,
                                                fontWeight = FontWeight.Medium
                                            )
                                            Text(
                                                text = entry.date.toString(),
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        Text(
                                            text = "Value: ${entry.mood.value}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Mood Types Section
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "How are you feeling?",
                        fontWeight = FontWeight.Medium
                    )
                    
                    if (uiState.isLoadingTypes) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.moodTypes) { moodType ->
                                Button(
                                    onClick = { viewModel.selectMood(moodType.value) },
                                    modifier = Modifier.fillMaxWidth(),
                                    enabled = !uiState.isLoading
                                ) {
                                    Text(moodType.label)
                                }
                            }
                        }
                    }
                }
            }

            // History Button
            Button(
                onClick = { viewModel.loadHistory() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoadingHistory
            ) {
                Text("View History")
            }
        }
    }
} 