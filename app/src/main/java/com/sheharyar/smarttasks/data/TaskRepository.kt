package com.sheharyar.smarttasks

import android.icu.text.CaseMap.Title
import com.sheharyar.smarttasks.data.local.TaskDao
import com.sheharyar.smarttasks.model.Task
import com.sheharyar.smarttasks.model.TaskEntity
import com.sheharyar.smarttasks.model.TasksResponse
import com.sheharyar.smarttasks.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class TaskRepository @Inject constructor(
    private val apiService: ApiService,
    private val taskDao: TaskDao
) {

    fun getTasksForDate(date: String): Flow<TasksResponse> = flow {
        val tasksResponse = apiService.getTasks()
        val taskEntities = tasksResponse.tasks.map { task ->
            TaskEntity(
                id = task.id ?: "",
                Title = task.Title,
                Description = task.Description,
                DueDate = task.DueDate,
                Priority = task.Priority,
                TargetDate = task.TargetDate
            )
        }
        // Since we're not inserting into the database, we can emit the list directly
        emit(tasksResponse)
    }.flowOn(Dispatchers.IO)
}
