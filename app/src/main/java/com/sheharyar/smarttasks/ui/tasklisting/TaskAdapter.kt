package com.sheharyar.smarttasks.ui.tasklisting

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.sheharyar.smarttasks.databinding.ItemTaskBinding
import com.sheharyar.smarttasks.model.Task
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class TaskAdapter(private var tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        // Set Task Title
        holder.binding.tvTaskTitle.text = task.Title ?: "Untitled Task"

        // Set Due Date Value
        holder.binding.tvDueDateValue.text = task.DueDate ?: "N/A"

        // Calculate and Set Days Left Value
        holder.binding.tvDaysLeftValue.text = calculateDaysLeft(task.DueDate)

        // Additional data binding if needed
    }

    override fun getItemCount() = tasks.size

    // Method to update tasks
    fun updateTasks(newTasks: List<Task>) {
        this.tasks = newTasks
        notifyDataSetChanged()
    }

    // Function to calculate days left until due date
    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateDaysLeft(dueDateStr: String?): String {
        return if (!dueDateStr.isNullOrEmpty()) {
            try {
                val dueDate = LocalDate.parse(dueDateStr, DateTimeFormatter.ISO_DATE)
                val currentDate = LocalDate.now()
                val daysLeft = ChronoUnit.DAYS.between(currentDate, dueDate)
                when {
                    daysLeft > 0 -> "$daysLeft"
                    daysLeft == 0L -> "Today"
                    else -> "Overdue"
                }
            } catch (e: Exception) {
                "Invalid date"
            }
        } else {
            "N/A"
        }
    }
}
