package com.icyapps.moodtracker.di

import com.icyapps.moodtracker.data.remote.MoodApiService
import com.icyapps.moodtracker.data.remote.MoodApiServiceImpl
import com.icyapps.moodtracker.data.repository.MoodRepositoryImpl
import com.icyapps.moodtracker.domain.repository.MoodRepository
import com.icyapps.moodtracker.domain.usecase.CanSubmitMoodTodayUseCase
import com.icyapps.moodtracker.domain.usecase.GetAllMoodTypesUseCase
import com.icyapps.moodtracker.domain.usecase.GetMoodHistoryUseCase
import com.icyapps.moodtracker.domain.usecase.SubmitMoodUseCase
import com.icyapps.moodtracker.presentation.viewmodel.MoodViewModel
import io.ktor.client.*
import org.koin.dsl.module

val appModule = module {
    
    // HTTP Client
    single<HttpClient> {
        MoodApiServiceImpl.createHttpClient(Config.API_KEY)
    }
    
    // API Service
    single<MoodApiService> {
        MoodApiServiceImpl(
            httpClient = get(),
            baseUrl = Config.API_BASE_URL
        )
    }
    
    // Repository
    single<MoodRepository> {
        MoodRepositoryImpl(
            apiService = get()
        )
    }
    
    // Use Cases
    single {
        CanSubmitMoodTodayUseCase(
            repository = get()
        )
    }
    
    single {
        GetAllMoodTypesUseCase(
            repository = get()
        )
    }
    
    single {
        SubmitMoodUseCase(
            repository = get()
        )
    }
    
    single {
        GetMoodHistoryUseCase(
            repository = get()
        )
    }
    
    // ViewModels
    single {
        MoodViewModel(
            canSubmitMoodTodayUseCase = get(),
            getAllMoodTypesUseCase = get(),
            submitMoodUseCase = get(),
            getMoodHistoryUseCase = get()
        )
    }
} 