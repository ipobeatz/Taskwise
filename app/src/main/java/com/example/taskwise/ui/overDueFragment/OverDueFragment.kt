package com.example.taskwise.ui.overDueFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskwise.databinding.FragmentOverDueBinding
import com.example.taskwise.ui.todoFragment.TodoViewModel
import com.example.taskwise.util.DateChecker.getDifferentDays
import com.example.taskwise.util.TimeChecker.checkTime
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OverDueFragment : Fragment() {

    private var _binding: FragmentOverDueBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var overDueAdapter: OverDueAdapter
    //private val overDueViewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverDueBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeToLiveData()
       // overDueViewModel.getAllTasks()
    }

    private fun setUpRecyclerView() {
        binding.overDueRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = overDueAdapter
        }
    }

    private fun observeToLiveData() {
        /*overDueViewModel.taskData.observe(viewLifecycleOwner) { tasks ->
            val overdueTasks = tasks.filter {
                getDifferentDays(it.date) < 0 || (getDifferentDays(it.date) == 0 && !checkTime(it.time))
            }
            overDueAdapter.differ.submitList(overdueTasks)
        }

         */
    }
}
