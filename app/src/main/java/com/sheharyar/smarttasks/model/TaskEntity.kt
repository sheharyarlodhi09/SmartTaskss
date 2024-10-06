package com.sheharyar.smarttasks.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val Title: String?,
    val Description: String?,
    val DueDate: String?,
    val Priority: String?,
    val TargetDate: String?
)

