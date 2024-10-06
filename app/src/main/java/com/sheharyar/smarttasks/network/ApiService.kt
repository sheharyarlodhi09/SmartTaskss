package com.sheharyar.smarttasks.network

import com.sheharyar.smarttasks.model.Task
import com.sheharyar.smarttasks.model.TasksResponse
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun getTasks(): TasksResponse
}