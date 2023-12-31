package com.example.taskwise.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.renderscript.RenderScript
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.DialogFragment
import com.example.taskwise.R
import com.example.taskwise.data.model.Priority
import com.example.taskwise.data.model.Task
import com.example.taskwise.databinding.AddTaskDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class AddTaskDialog @Inject constructor() : DialogFragment() {

    private lateinit var listener: OnInputListener
    private var priority: Priority = Priority.LOW

    private var _binding: AddTaskDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddTaskDialogBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun isRadioGroupEmpty(): Boolean {
            return binding.radioGroup.checkedRadioButtonId == -1
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (isRadioGroupEmpty()) {
                Toast.makeText(context, "Please select an option", Toast.LENGTH_LONG).show()
            }
        }
        binding.dateOfTaskEditText.setOnClickListener {
            openDatePickerDialog(it, requireActivity())
        }

        binding.timeOfTaskEditText.setOnClickListener {
            showTimePicker(it, requireActivity())
        }

        binding.addBtn.setOnClickListener {
            if (isDialogEmpty())
                Toast.makeText(activity, "Fill all views", Toast.LENGTH_LONG).show()
            else {
                when (binding.radioGroup.checkedRadioButtonId) {
                    R.id.lowRadiobtn -> priority = Priority.LOW
                    R.id.mediumRadiobtn -> priority = Priority.MEDIUM
                    R.id.highRadiobtn -> priority = Priority.HIGH
                }
                val task = Task(
                    binding.titleOfTaskEditText.text.toString(),
                    binding.dateOfTaskEditText.text.toString(),
                    binding.timeOfTaskEditText.text.toString(),
                    priority,
                    binding.reminderSwitch.isChecked
                )
                listener.sendInput(task)
                dialog!!.dismiss()
            }
        }

        binding.cancelBtn.setOnClickListener {
            dialog!!.dismiss()
        }
        dialog!!.show()
    }


        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun isDialogEmpty(): Boolean {
        return binding.titleOfTaskEditText.text.isEmpty() ||
                binding.dateOfTaskEditText.text.isEmpty() ||
                binding.timeOfTaskEditText.text.isEmpty()

    }
    //soruna bak



    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = activity as OnInputListener
        } catch (e: ClassCastException) {
        }
    }

    private fun openDatePickerDialog(v: View, activity: Activity) {
        val cal: Calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            activity,
            { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                var selectedDate = dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
                if (monthOfYear + 1 < 10) selectedDate =
                    dayOfMonth.toString() + "/" + "0" + (monthOfYear + 1) + "/" + year
                when (v.id) {
                    R.id.dateOfTaskEditText -> (v as EditText).setText(selectedDate)
                }
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = cal.timeInMillis - 1
        datePickerDialog.show()
    }

    private fun showTimePicker(view: View, activity: Activity) {
        val currentTime = Calendar.getInstance()
        val hour = currentTime[Calendar.HOUR_OF_DAY]
        val minute = currentTime[Calendar.MINUTE]
        val timePickerDialog = TimePickerDialog(activity, { timePicker, selectedHour, selectedMinute ->
                val selectedTime = "$selectedHour:$selectedMinute"
                when (view.id) {
                    R.id.timeOfTaskEditText -> (view as EditText).setText(selectedTime)
                }
            }, hour, minute, true
        )
        timePickerDialog.setTitle("Select Time Of task")
        timePickerDialog.show()
    }
}