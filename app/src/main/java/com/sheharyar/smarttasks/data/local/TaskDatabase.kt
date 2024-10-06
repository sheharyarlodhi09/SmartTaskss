package com.sheharyar.smarttasks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sheharyar.smarttasks.model.TaskEntity

@Database(entities = [TaskEntity::class], version = 4)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
