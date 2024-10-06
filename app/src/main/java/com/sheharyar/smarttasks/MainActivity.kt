package com.sheharyar.smarttasks

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.sheharyar.smarttasks.databinding.ActivityMainBinding
import com.sheharyar.smarttasks.ui.tasklisting.TaskAdapter
import com.sheharyar.smarttasks.ui.tasklisting.TaskViewModel
import com.sheharyar.smarttasks.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setup View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the current date or a date of your choice
        val currentDate = LocalDate.now().toString() // Format as needed

        // Fetch tasks for the current date
        taskViewModel.getTasksForDate(currentDate)

        // Observe tasksState
        lifecycleScope.launchWhenStarted {
            taskViewModel.tasksState.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.rvTasks.visibility = View.GONE
                        binding.tvEmpty.visibility = View.GONE
                    }

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val tasks = resource.data
                        if (tasks.isEmpty()) {
                            binding.rvTasks.visibility = View.GONE
                            binding.tvEmpty.visibility = View.VISIBLE
                        } else {
                            binding.rvTasks.visibility = View.VISIBLE
                            binding.tvEmpty.visibility = View.GONE
                            taskAdapter = TaskAdapter(tasks)
                            binding.rvTasks.adapter = taskAdapter
                        }
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@MainActivity,
                            "Error: ${resource.exception}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        // Title
        taskViewModel.date.onEach { date ->
            binding.tvTitle.text = "Tasks for ${date.format(DateTimeFormatter.ISO_DATE)}"
        }.launchIn(lifecycleScope)
    }
}