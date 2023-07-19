package com.example.taskwise.ui.overDueFragment

import android.annotation.SuppressLint
import android.renderscript.RenderScript
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskwise.R
import com.example.taskwise.data.model.Priority
import com.example.taskwise.data.model.Task
import com.example.taskwise.databinding.OverdueItemBinding
import javax.inject.Inject

class OverDueAdapter @Inject constructor() :
    RecyclerView.Adapter<OverDueAdapter.OverDueViewHolder>() {

    inner class OverDueViewHolder(val binding: OverdueItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OverDueAdapter.OverDueViewHolder {
        val view = OverdueItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OverDueViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OverDueAdapter.OverDueViewHolder, position: Int) {

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
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}

