package com.example.taskwise.ui.dialog

import com.example.taskwise.data.model.Task

interface OnInputListener {
    fun sendInput(input: Task)

}