package com.example.taskwise

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import com.example.taskwise.data.model.Task
import com.example.taskwise.databinding.ActivityMainBinding
import com.example.taskwise.ui.dialog.AddTaskDialog
import com.example.taskwise.ui.dialog.OnInputListener
import com.example.taskwise.ui.todoFragment.TodoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnInputListener {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: TodoViewModel by viewModels()
    private lateinit var task: Task

    @Inject
    lateinit var addTaskDialog: AddTaskDialog



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        AndroidThreeTen.init(this)
        val today: LocalDate = LocalDate.now()
        val dayName = today.dayOfWeek.name
        val year = today.year
        val month = today.month
        binding.dateOfDayTextView.text = "$dayName, $month, $year"

        binding.addTaskBtn.setOnClickListener {

            val addTaskDialog = AddTaskDialog()
            addTaskDialog.show(supportFragmentManager, "AddTaskDialog")
        }

    }

    override fun sendInput(input: Task) {
        task = input
        addTask()
    }

    private fun addTask() {
        viewModel.insertTask(task)
    }


}