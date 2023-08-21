package com.example.taskwise.ui.todoFragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskwise.R
import com.example.taskwise.data.model.Task
import com.example.taskwise.databinding.FragmentTodoBinding
import com.example.taskwise.ui.completedFragment.CompletedTaskViewModel
import com.example.taskwise.util.DateChecker.getDifferentDays
import com.example.taskwise.util.TimeChecker.checkTime
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ToDoFragment : Fragment() {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private var isButtonVisible = true
    private val todoViewModel: TodoViewModel by viewModels()
    private val completedTaskViewModel: CompletedTaskViewModel by viewModels()
    private val todoAdapter: TodoAdapter by lazy { TodoAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTodoBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeToLiveData()

        binding.todoRecyclerView.scrollToPosition(0) // RecyclerView'ı en üstte başlat
        // Diğer kodlar...


        todoAdapter.setOnCheckBoxClickListener(object : OnCheckBoxClickListener {
            override fun OnCheckBoxClicked(task: Task, position: Int) {
                todoViewModel.deleteTask(task)
                completedTaskViewModel.insertCompletedTask(task)
                Toast.makeText(requireContext(), "Completed ", Toast.LENGTH_LONG).show()
            }
        })


        todoAdapter.setOnCheckBtnClickListener(object : OnCheckBoxClickListener {
            override fun OnCheckBoxClicked(task: Task, position: Int) {
                todoViewModel.deleteTask(task)
                completedTaskViewModel.insertCompletedTask(task)
                Toast.makeText(requireContext(), "Completed ", Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun setupRecyclerView(tasks: List<Task>) {
        todoAdapter.submitData(tasks)
        // RecyclerView diğer yapılandırmaları ve ayarlamaları
    }

    private fun setUpRecyclerView() {
        binding.todoRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
            scrollToPosition(0) // RecyclerView'ı en üstte başlat
        }
    }



    private fun observeToLiveData() {
        todoViewModel.getAllTasks.observe(viewLifecycleOwner) { tasks ->
            val filteredTasks = tasks.filter {
                getDifferentDays(it.date) > 0 || (getDifferentDays(it.date) == 0 && checkTime(it.time))
            }

            val previousItemCount = todoAdapter.itemCount // Önceki öğe sayısını alın
            todoAdapter.submitData(filteredTasks.reversed())
            removeNoTaskLayout(filteredTasks)

            val newItemCount = todoAdapter.itemCount // Yeni öğe sayısını alın

            if (newItemCount > previousItemCount) {
                binding.todoRecyclerView.scrollToPosition(0)
            }
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