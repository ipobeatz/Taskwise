package com.example.taskwise.ui.completedFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskwise.data.model.Task
import com.example.taskwise.databinding.FragmentCompletedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CompletedFragment : Fragment() {

    private var _binding: FragmentCompletedBinding? = null
    private val binding get() = _binding!!
    private val completedTasks = mutableListOf<Task>()

    @Inject
    lateinit var completedTaskAdapter: CompletedTaskAdapter
    private val completedTaskViewModel: CompletedTaskViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompletedBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        observeToLiveData()



/*
        viewLifecycleOwner.lifecycleScope.launch {
            completedTaskViewModel.tasks.collectLatest { pagingData ->
                completedTaskAdapter.submitData(pagingData)
            }
        }
*/
    }



    private fun setUpRecyclerView() {
        val recyclerView = binding.completedRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = completedTaskAdapter

    }

    private fun observeToLiveData() {
        completedTaskViewModel.getAllCompletedTasks.observe(viewLifecycleOwner) { completedtasks ->
            completedTaskAdapter.differ.submitList(completedtasks.asReversed())

        }
        completedTaskViewModel.getAllCompletedTasks.observe(viewLifecycleOwner) { completedtasks ->
            completedTaskAdapter.differ.submitList(completedtasks.asReversed())

        }
    }

}