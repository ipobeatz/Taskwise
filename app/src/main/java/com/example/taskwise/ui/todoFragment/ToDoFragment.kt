package com.example.taskwise.ui.todoFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskwise.data.model.Task
import com.example.taskwise.databinding.FragmentTodoBinding
import com.example.taskwise.ui.completedFragment.CompletedTaskViewModel
import com.example.taskwise.ui.dialog.AddTaskDialog
import com.example.taskwise.util.DateChecker.getDifferentDays
import com.example.taskwise.util.TimeChecker.checkTime
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ToDoFragment : Fragment() {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private val todoViewModel: TodoViewModel by viewModels()
    private val todoAdapter: TodoAdapter by lazy { TodoAdapter() }
    private var isDataDeleted = false

    @Inject
    lateinit var addTaskDialog: AddTaskDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeToLiveData()
        todoViewModel.getAllTasks()

        todoAdapter.setOnCheckBtnClickListener(object : OnCheckBoxClickListener {
            override fun OnCheckBoxClicked(task: Task, position: Int) {
                todoViewModel.deleteTask(task)
                Toast.makeText(requireContext(), "Completed ", Toast.LENGTH_LONG).show()
                isDataDeleted = true
            }
        })
    }

    private fun setUpRecyclerView() {
        binding.todoRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }
    }

    private fun observeToLiveData() {
        todoViewModel.getAllTasks().observe(viewLifecycleOwner) { tasks ->
            println("mcmc getalltasks")
            val filteredTasks = tasks.filter {
                getDifferentDays(it.date) > 0 || (getDifferentDays(it.date) == 0 && checkTime(it.time))
            }

            val previousItemCount = todoAdapter.itemCount // Önceki öğe sayısını alın
            todoAdapter.submitData(filteredTasks.reversed())
            removeNoTaskLayout(filteredTasks)

            val newItemCount = todoAdapter.itemCount // Yeni öğe sayısını alın
        }

        todoViewModel.inserted.observe(viewLifecycleOwner) {
            println("mcmc inserted--")
        }
    }

    private fun removeNoTaskLayout(tasks: List<Task>?) {
        if (tasks == null || tasks.isEmpty()) {
            binding.noTaskLayout.visibility = View.VISIBLE
            binding.todoRecyclerView.visibility = View.GONE
        } else {
            binding.noTaskLayout.visibility = View.GONE
            binding.todoRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
