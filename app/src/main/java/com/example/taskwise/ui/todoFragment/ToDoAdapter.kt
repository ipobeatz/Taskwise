package com.example.taskwise.ui.todoFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskwise.R
import com.example.taskwise.data.model.Priority
import com.example.taskwise.data.model.Task
import com.example.taskwise.databinding.TaskItemBinding
import javax.inject.Inject

class TodoAdapter @Inject constructor() : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(),
    OnCheckBoxClickListener {
    private var onCheckBoxClickListener: OnCheckBoxClickListener? = null

    fun setOnCheckBoxClickListener(listener: OnCheckBoxClickListener) {
        onCheckBoxClickListener = listener
    }


    inner class TodoViewHolder(val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

            binding.checkbox.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = differ.currentList[position]
                    onCheckBoxClickListener?.OnCheckBoxClicked(task, position)
                }
            }
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder {
        val view = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {

        val currentItem = differ.currentList[position]

        holder.binding.taskTitle.text = currentItem.title
        holder.binding.timeTextView.text = currentItem.time
        holder.binding.dateOfDayTextView.text = currentItem.date
        if (!currentItem.isReminder)
            holder.binding.reminderView.visibility = View.GONE

        if (currentItem.priority == Priority.MEDIUM)
            holder.binding.taskPriority.setBackgroundResource(R.drawable.priority_medium)
        else if (currentItem.priority == Priority.HIGH)
            holder.binding.taskPriority.setBackgroundResource(R.drawable.priority_high)

        holder.binding.checkbox.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION && position < differ.currentList.size) {
                listener?.OnCheckBoxClicked(differ.currentList[position], position)
            }
        }
        holder.binding.checkbox.isChecked = false
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnCheckBtnClickListener(listener: OnCheckBoxClickListener) {
        TodoAdapter.listener = listener
    }

    override fun OnCheckBoxClicked(task: Task, position: Int) {

        // Tıklanan öğeyle ilgili işlemleri gerçekleştirin.
        // Örneğin, öğeyi listeden kaldırabilir veya diğer güncellemeleri yapabilirsiniz.


    }

    companion object {
        var listener: OnCheckBoxClickListener? = null
    }

    fun submitData(taskList: List<Task>): Int {
        val previousItemCount = itemCount
        differ.submitList(taskList)
        val newItemCount = itemCount
        return newItemCount - previousItemCount
    }
}