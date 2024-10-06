package com.sheharyar.smarttasks.model

import com.google.gson.annotations.SerializedName

data class TasksResponse(
    @SerializedName("tasks")
    val tasks: List<Task>
)

