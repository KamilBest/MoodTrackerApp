package com.icyapps.moodtrackerbackend.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "mood_entry", uniqueConstraints = [
    UniqueConstraint(columnNames = ["deviceId", "date"])
])
data class MoodEntry(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    val deviceId: String,

    @Enumerated(EnumType.STRING)
    val mood: MoodType,

    val date: LocalDate = LocalDate.now(),

    val createdAt: LocalDateTime = LocalDateTime.now()
)