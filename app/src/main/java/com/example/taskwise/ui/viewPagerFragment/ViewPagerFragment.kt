package com.example.taskwise.ui.viewPagerFragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.taskwise.R
import com.example.taskwise.databinding.FragmentViewPagerBinding
import com.example.taskwise.ui.completedFragment.CompletedFragment
import com.example.taskwise.ui.overDueFragment.OverDueFragment
import com.example.taskwise.ui.todoFragment.ToDoFragment
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("ResourceAsColor")
@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = listOf(
            ToDoFragment(),
            CompletedFragment(),
            OverDueFragment()
        )

        val adapter =
            ViewPagerAdapter(fragments, childFragmentManager, viewLifecycleOwner.lifecycle)

        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when (position) {
                    0 -> setTodoBackground()
                    1 -> setCompletedBackground()
                    2 -> setOverDueBackground()
                }
            }
        })

        binding.todoTab.setOnClickListener {
            setTodoBackground()
        }

        binding.completedTab.setOnClickListener {
            setCompletedBackground()
        }

        binding.overdueTab.setOnClickListener {
            setOverDueBackground()
        }
    }

    private fun setTodoBackground() {
        binding.viewPager.setCurrentItem(0, true)
        with(binding) {
            todoTab.setTextColor(requireContext().resources.getColor(R.color.orange_light))
            completedTab.setTextColor(requireContext().resources.getColor(R.color.white))
            overdueTab.setTextColor(requireContext().resources.getColor(R.color.white))
        }
    }

    private fun setCompletedBackground() {
        binding.viewPager.setCurrentItem(1, true)
        with(binding) {
            completedTab.setTextColor(requireContext().resources.getColor(R.color.orange_light))
            todoTab.setTextColor(requireContext().resources.getColor(R.color.white))
            overdueTab.setTextColor(requireContext().resources.getColor(R.color.white))
        }
    }

    private fun setOverDueBackground() {
        binding.viewPager.setCurrentItem(2, true)
        with(binding) {
            overdueTab.setTextColor(requireContext().resources.getColor(R.color.orange_light))
            completedTab.setTextColor(requireContext().resources.getColor(R.color.white))
            todoTab.setTextColor(requireContext().resources.getColor(R.color.white))
        }
    }
}
