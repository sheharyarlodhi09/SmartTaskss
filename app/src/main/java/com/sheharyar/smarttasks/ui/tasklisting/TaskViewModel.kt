package com.sheharyar.smarttasks.ui.tasklisting

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sheharyar.smarttasks.TaskRepository
import com.sheharyar.smarttasks.model.Task
import com.sheharyar.smarttasks.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val _date = MutableStateFlow(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val date: StateFlow<LocalDate> = _date.asStateFlow()

    private val _tasksState = MutableStateFlow<Resource<List<Task>>>(Resource.Loading)
    val tasksState: StateFlow<Resource<List<Task>>> = _tasksState

    fun getTasksForDate(date: String) {
        viewModelScope.launch {
            _tasksState.value = Resource.Loading
            try {
                repository.getTasksForDate(date)
                    .collect { tasks ->
                        _tasksState.value = Resource.Success(tasks.tasks)
                    }
            } catch (e: Exception) {
                _tasksState.value = Resource.Error(e)
            }
        }
    }
}
