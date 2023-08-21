package com.example.taskwise.ui.todoFragment

import com.example.taskwise.data.model.Task

interface OnCheckBoxClickListener {
    fun OnCheckBoxClicked(task: Task, position: Int)
}

